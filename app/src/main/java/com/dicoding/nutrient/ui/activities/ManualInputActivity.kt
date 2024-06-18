package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityManualInputBinding

class ManualInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManualInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, NutritionScanFailActivity::class.java))
        }

        binding.btSaveHistory.setOnClickListener {
            // Logic to save the history
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btSaveHistory.setButtonEnableFillFnBManual(
                    etFoodNameResult = binding.edFoodAndBev.text.toString(),
                    etPortionResult = binding.edPortion.text.toString(),
                    etServingGramResult = binding.edServing.text.toString(),
                    etProteinResult = binding.edProtein.text.toString(),
                    etFatResult = binding.edFat.text.toString(),
                    etCarbohydrateResult = binding.edCarbo.text.toString()
                )
            }

        }

        binding.edFoodAndBev.addTextChangedListener(textWatcher)
        binding.edPortion.addTextChangedListener(textWatcher)
        binding.edServing.addTextChangedListener(textWatcher)
        binding.edProtein.addTextChangedListener(textWatcher)
        binding.edFat.addTextChangedListener(textWatcher)
        binding.edCarbo.addTextChangedListener(textWatcher)
    }
}
