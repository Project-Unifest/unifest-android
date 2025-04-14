package com.unifest.android.core.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LikedFestivalDatabaseMigrationTest {
    private val LIKED_FESTIVAL_TEST_DB = "liked_festival_migration_test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        LikedFestivalDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory()
    )

    // 버전 1에서의 테이블 스키마 생성
    @Test
    @Throws(IOException::class)
    fun createV1Database() {
        helper.createDatabase(LIKED_FESTIVAL_TEST_DB, 1).apply {
            // 버전 1 DB에 필요한 테이블 스키마 생성
            execSQL(
                """
                CREATE TABLE IF NOT EXISTS `liked_festival` (
                    `festival_id` INTEGER NOT NULL,
                    `school_id` INTEGER NOT NULL,
                    `thumbnail` TEXT NOT NULL,
                    `school_name` TEXT NOT NULL,
                    `region` TEXT,
                    `festival_name` TEXT NOT NULL,
                    `begin_date` TEXT NOT NULL,
                    `end_date` TEXT NOT NULL,
                    `star_list` TEXT,
                    `latitude` REAL,
                    `longitude` REAL,
                    PRIMARY KEY(`festival_id`)
                )
                """
            )

            // 샘플 데이터 삽입
            val values = ContentValues().apply {
                put("festival_id", 1)
                put("school_id", 2)
                put("thumbnail", "https://example.com/konkuk_mark.jpg")
                put("school_name", "건국대")
                put("region", "서울")
                put("festival_name", "대동제")
                put("begin_date", "2024-05-21")
                put("end_date", "2024-05-24")
                putNull("star_list")
                put("latitude", 36.969868f)
                put("longitude", 127.871726f)
            }

            insert("liked_festival", SQLiteDatabase.CONFLICT_REPLACE, values)
            close()
        }
    }

    // 버전 1에서 버전 2로의 마이그레이션 테스트
    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        // 버전 1 데이터베이스 생성 및 데이터 추가
        helper.createDatabase(LIKED_FESTIVAL_TEST_DB, 1).apply {
            val values = ContentValues().apply {
                put("festival_id", 2)
                put("school_id", 12)
                put("thumbnail", "https://example.com/knut_mark.jpg")
                put("school_name", "한국교통대학교")
                put("region", "충주")
                put("festival_name", "Young:one")
                put("begin_date", "2024-10-29")
                put("end_date", "2024-10-30")
                putNull("star_list")
                put("latitude", 36.969868f)
                put("longitude", 127.87126f)
            }

            insert("liked_festival", SQLiteDatabase.CONFLICT_REPLACE, values)
            close()
        }

        // 마이그레이션 실행
        val db = helper.runMigrationsAndValidate(LIKED_FESTIVAL_TEST_DB, 2, true, MIGRATION_1_2)

        // 마이그레이션 후 새 컬럼이 추가되었는지 확인
        val cursor = db.query("SELECT * FROM liked_festival WHERE festival_id = 2")
        cursor.use {
            it.moveToFirst()
            val festivalIdIdx = it.getColumnIndex("festival_id")
            val createdAtIdx = it.getColumnIndex("created_at")

            assertEquals(2, it.getLong(festivalIdIdx))
            // created_at 컬럼이 존재하고 기본값이 들어가 있는지 확인
            assert(createdAtIdx != -1)
            assert(it.getLong(createdAtIdx) > 0)
        }
    }

    // 전체 마이그레이션 경로를 테스트 (버전 1부터 최신 버전까지)
    @Test
    @Throws(IOException::class)
    fun migrateAllVersions() {
        // 버전 1로 시작하는 DB 생성
        helper.createDatabase(LIKED_FESTIVAL_TEST_DB, 1).close()

        // Room이 지원하는 모든 마이그레이션으로 DB 열기
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            LikedFestivalDatabase::class.java,
            LIKED_FESTIVAL_TEST_DB
        ).addMigrations(MIGRATION_1_2)
            .build()
            .apply {
                // DB가 올바르게 열리는지 검증
                openHelper.writableDatabase
                close()
            }
    }
}
