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

fun String.GetDataScanNutrition(): Map<String, String> {
    val resultTrim = this.trimIndent()
    val regex = Regex("""Lemak Total\s+(\d+\.\d+)\s+Lemak Jenuh\s+\d+\s+Protein\s+(\d+)\s+Karbohidrat Total\s+(\d+)""")
    val matchResult = regex.find(resultTrim)

    return if (matchResult != null){
        val lemakTotal = matchResult.groupValues[1]
        val protein = matchResult.groupValues[2]
        val karbohidratTotal = matchResult.groupValues[3]
        mapOf(
            "Lemak" to lemakTotal,
            "Protein" to protein,
            "Karbohidrat" to karbohidratTotal
        )
    } else {
        emptyMap()
    }
}