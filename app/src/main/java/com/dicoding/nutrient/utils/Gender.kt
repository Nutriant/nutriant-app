package com.dicoding.nutrient.utils

enum class Gender(val genderValue: Int) {
    Male(1),
    Female(0);

    companion object {
        fun fromInt(value: Int): Gender? {
            return entries.find { it.genderValue == value }
        }
    }
}