package com.unifest.android.core.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Parses the string as a LocalDate using the provided pattern, defaulting to "yyyy-MM-dd".
 */
fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, dateFormatter)
}

/**
 * Formats LocalDate to a string with the specified pattern, defaulting to "M.d".
 */
fun LocalDate.formatToString(pattern: String = "M.d"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

/**
 * Formats LocalDate to a string including day of week, e.g., "5/21(Tue)".
 */
fun LocalDate.formatWithDayOfWeek(): String {
    val formatter = DateTimeFormatter.ofPattern("M/d(E)", Locale.KOREAN)
    return this.format(formatter)
}
