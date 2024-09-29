package com.unifest.android.core.common.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// 포매터 정의
val formatter = DateTimeFormatter.ofPattern("HH:mm")
val parser = DateTimeFormatter.ofPattern("HH:mm:ss")

// 시간 파싱 및 형식화 함수
fun parseAndFormatTime(time: String?): Pair<String, LocalTime?> {
    return if (time.isNullOrBlank() || time == "등록된 정보가 없습니다") {
        "등록된 정보가 없습니다" to null
    } else {
        try {
            val localTime = LocalTime.parse(time, parser)
            localTime.format(formatter) to localTime
        } catch (e: DateTimeParseException) {
            "등록된 정보가 없습니다" to null
        }
    }
}
