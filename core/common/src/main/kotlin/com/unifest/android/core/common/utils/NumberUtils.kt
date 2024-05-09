package com.unifest.android.core.common.utils

/**
 * Extends Int to format as a currency string with comma separators.
 */
fun Int.formatAsCurrency(): String {
    return "%,d원".format(this)
}

/**
 * Extends Float to format as a currency string with comma separators and two decimal places.
 */
fun Float.formatAsCurrency(): String {
    return "%,.2f원".format(this)
}
