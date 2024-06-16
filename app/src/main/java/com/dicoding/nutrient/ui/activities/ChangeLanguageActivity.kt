package com.dicoding.nutrient.ui.activities
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.dicoding.nutrient.databinding.ActivityChangeLanguageBinding
import java.util.Locale

class ChangeLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentLocale = Locale.getDefault().language
        binding.radioEnglish.isChecked = currentLocale == "en"
        binding.radioIndonesia.isChecked = currentLocale == "in"

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                com.dicoding.nutrient.R.id.radioEnglish -> changeLocale("en")
                com.dicoding.nutrient.R.id.radioIndonesia -> changeLocale("in")
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun changeLocale(localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val resources: Resources = resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeCode))
        recreate()
    }
}