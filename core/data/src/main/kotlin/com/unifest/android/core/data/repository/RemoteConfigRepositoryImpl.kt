package com.unifest.android.core.data.repository

import com.unifest.android.core.data.BuildConfig
import com.unifest.android.core.data.datasource.RemoteConfigDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

internal class RemoteConfigRepositoryImpl @Inject constructor(
    private val remoteConfigDataSource: RemoteConfigDataSource,
) : RemoteConfigRepository {
    override fun shouldUpdate(): Flow<Boolean> = flow {
        remoteConfigDataSource.getString(KEY_MIN_VERSION)?.let { minVersion ->
            val currentVersion = BuildConfig.APP_VERSION
            emit(checkMinVersion(currentVersion, minVersion))
        } ?: run {
            Timber.d("shouldUpdate: min version 가져오기 실패")
            emit(false)
        }
    }

    companion object {
        const val KEY_MIN_VERSION = "MinVersion"

        /**
         * @return true 이면 업데이트 필요
         */
        fun checkMinVersion(currentVersion: String, minVersion: String): Boolean {
            Timber.d("checkMinVersion: current: $currentVersion, min: $minVersion")
            if (!Regex("""^\d+\.\d+(\.\d+)?$""").matches(currentVersion)) return false
            if (!Regex("""^\d+\.\d+(\.\d+)?$""").matches(minVersion)) return false

            return currentVersion.split('.').map(String::toInt).let {
                val min = minVersion.split('.').map(String::toInt)
                !KotlinVersion(it[0], it[1], it.getOrNull(2) ?: 0)
                    .isAtLeast(min[0], min[1], min.getOrNull(2) ?: 0)
            }
        }
    }
}
