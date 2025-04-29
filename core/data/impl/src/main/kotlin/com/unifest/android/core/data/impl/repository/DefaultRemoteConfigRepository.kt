package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.datasource.RemoteConfigDataSource
import com.unifest.android.core.data.api.repository.RemoteConfigRepository
import com.unifest.android.core.data.impl.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

internal class DefaultRemoteConfigRepository @Inject constructor(
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

            val current = currentVersion.split('.').map { it.toInt() }
            val min = minVersion.split('.').map { it.toInt() }

            // 메이저 버전 비교
            if (current[0] != min[0]) return current[0] < min[0]

            // 마이너 버전 비교
            if (current[1] != min[1]) return current[1] < min[1]

            // 패치 버전 비교
            return current[2] < min[2]
        }
    }
}
