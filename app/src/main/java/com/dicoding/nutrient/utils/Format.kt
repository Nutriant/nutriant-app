package com.dicoding.nutrient.utils

fun formatNumber(number: String): String {
    val value = number.toDouble()
    return String.format("%.1f", value)
}