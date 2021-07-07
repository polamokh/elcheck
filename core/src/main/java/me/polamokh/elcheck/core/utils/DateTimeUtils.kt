package me.polamokh.elcheck.core.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_ISO8601_PATTERN = "yyyy-MM-dd hh:mm a"

fun Date.getNowFormattedDateTime(): String {
    return SimpleDateFormat(
        DATE_TIME_ISO8601_PATTERN,
        Locale.getDefault()
    ).format(this)
}