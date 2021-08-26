package me.polamokh.elcheck.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_ISO8601_PATTERN = "dd-MM-yyyy hh:mm a"

fun Date.getNowFormattedDateTime(): String {
    return SimpleDateFormat(
        DATE_TIME_ISO8601_PATTERN,
        Locale.ENGLISH
    ).format(this)
}

fun String.getLocaleFormattedDateTime(locale: Locale? = null): String {
    val dateTime = SimpleDateFormat(DATE_TIME_ISO8601_PATTERN, Locale.ENGLISH)
        .parse(this) ?: throw Exception("Unparseable date.")

    return SimpleDateFormat(
        DATE_TIME_ISO8601_PATTERN,
        locale ?: Locale.ENGLISH
    ).format(dateTime)
}