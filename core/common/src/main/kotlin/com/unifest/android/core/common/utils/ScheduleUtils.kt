package com.unifest.android.core.common.utils

import com.unifest.android.core.model.ScheduleModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * 부스가 현재 운영 중인지 확인하는 함수
 */
fun isBoothCurrentlyRunning(scheduleList: List<ScheduleModel>): Boolean {
    // 현재 시간과 날짜 가져오기
    val koreaZoneId = ZoneId.of("Asia/Seoul")
    val currentDateTime = ZonedDateTime.now(koreaZoneId)
    val currentTime = currentDateTime.toLocalTime()
    val currentDate = currentDateTime.toLocalDate()

    // 부스가 현재 운영 중인지 확인
    return scheduleList.any { schedule ->
        // 날짜 확인
        val scheduleDate = LocalDate.parse(schedule.date)
        val isToday = scheduleDate.equals(currentDate)

        // 오늘 날짜면 시간 확인
        if (isToday) {
            val openLocalTime = LocalTime.parse(schedule.openTime)
            val closeLocalTime = LocalTime.parse(schedule.closeTime)

            // 폐장 시간이 개장 시간보다 이른 경우(다음날로 넘어가는 경우)
            if (closeLocalTime.isBefore(openLocalTime)) {
                // 현재 시간이 개장 시간 이후면 운영 중
                currentTime.isAfter(openLocalTime) || currentTime.equals(openLocalTime)
            } else {
                // 일반적인 경우 - 같은 날 내에 운영 종료
                (currentTime.isAfter(openLocalTime) || currentTime.equals(openLocalTime)) &&
                    (currentTime.isBefore(closeLocalTime) || currentTime.equals(closeLocalTime))
            }
        } else {
            // 어제 날짜의 스케줄이고, 폐장 시간이 자정을 넘어가는 경우
            val yesterday = currentDate.minusDays(1)
            if (scheduleDate.equals(yesterday)) {
                val closeLocalTime = LocalTime.parse(schedule.closeTime)
                // 현재 시간이 폐장 시간보다 이전이면 아직 운영 중
                currentTime.isBefore(closeLocalTime) || currentTime.equals(closeLocalTime)
            } else {
                false
            }
        }
    }
}
