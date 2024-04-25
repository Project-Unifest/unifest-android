package com.unifest.android.core.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Parses the string as a LocalDate using the provided pattern, defaulting to "yyyy-MM-dd".
 */
fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, dateFormatter)
}
