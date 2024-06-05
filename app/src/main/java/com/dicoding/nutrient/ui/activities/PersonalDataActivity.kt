package com.dicoding.nutrient.ui.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityPersonalDataBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogSaveBinding
import com.dicoding.nutrient.ui.fragments.dashboard.ProfileFragment

class PersonalDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, DashboardWithBotNavActivity::class.java))
        }

        binding.btSaveProfile.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val dialog = Dialog(this)
        val dialogBinding = CustomPopupDialogSaveBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.btDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btDialogLogout.setOnClickListener {
            dialog.dismiss()
        }
    }
}