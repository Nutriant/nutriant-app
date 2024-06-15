package com.dicoding.nutrient.utils

fun String.getDataNutritionFromDesc(): Map<String, String> {
    val regex = Regex("""Calories:\s*(\d+)kcal\s*\|\s*Fat:\s*([\d.]+)g\s*\|\s*Carbs:\s*([\d.]+)g\s*\|\s*Protein:\s*([\d.]+)g""")
    val matchResult = regex.find(this)

    return if (matchResult != null) {
        val (calories, fat, carbs, protein) = matchResult.destructured
        mapOf(
            "Calories" to calories,
            "Fat" to fat,
            "Carbs" to carbs,
            "Protein" to protein
        )
    } else {
        emptyMap()
    }
}