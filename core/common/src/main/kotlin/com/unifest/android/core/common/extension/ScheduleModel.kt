package com.unifest.android.core.common.extension

import com.unifest.android.core.model.ScheduleModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * ScheduleModel을 "9월 16일 수 18:00-24:00" 형식으로 변환하는 확장 함수
 *
 * @return 날짜, 요일별 부스 운영시간
 */
fun ScheduleModel.toFormattedString(): String {
    // 날짜 파싱
    val localDate = LocalDate.parse(this.date)

    // 요일 구하기 (월, 화, 수, 목, 금, 토, 일)
    val dayOfWeekKorean = when (localDate.dayOfWeek.value) {
        1 -> "월"
        2 -> "화"
        3 -> "수"
        4 -> "목"
        5 -> "금"
        6 -> "토"
        7 -> "일"
        else -> ""
    }

    // 시간 파싱 및 포맷팅
    val openLocalTime = LocalTime.parse(this.openTime)
    val closeLocalTime = LocalTime.parse(this.closeTime)

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedOpenTime = openLocalTime.format(timeFormatter)
    val formattedCloseTime = closeLocalTime.format(timeFormatter)

    // 최종 형식 반환
    return "${localDate.monthValue}월 ${localDate.dayOfMonth}일 $dayOfWeekKorean $formattedOpenTime-$formattedCloseTime"
}
