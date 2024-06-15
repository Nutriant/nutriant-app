package com.dicoding.nutrient.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatNumber(number: String): String {
    val value = number.toDouble()
    return String.format("%.1f", value)
}

fun String.formatDateHistory() : String {
    val laravelDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    laravelDateFormat.timeZone = TimeZone.getTimeZone("UTC") // Menyetel timezone ke UTC
    val date: Date? = laravelDateFormat.parse(this)

    return if (date != null) {
        val desiredDateFormat = SimpleDateFormat("d MMM yyyy", Locale("id", "ID"))
        desiredDateFormat.format(date)
    } else {
        "Invalid date"
    }
}