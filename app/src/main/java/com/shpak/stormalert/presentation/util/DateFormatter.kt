package com.shpak.stormalert.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDay(pattern: String = "EEEE, MMM dd"): String {
    return formatDate(pattern)
}

fun Date.formatTime(pattern: String = "HH:mm"): String {
    return formatDate(pattern)
}

fun Date.formatDate(pattern: String = "MMM dd HH:mm"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
    return simpleDateFormat.format(this)
}