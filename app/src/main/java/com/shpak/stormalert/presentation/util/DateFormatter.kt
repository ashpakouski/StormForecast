package com.shpak.stormalert.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.formatUtcDate(pattern: String = "MMM dd HH:mm"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
    val localTime = Date(System.currentTimeMillis())
    val fromUtc = Date(this.time + TimeZone.getDefault().getOffset(localTime.time))
    return simpleDateFormat.format(fromUtc)
}