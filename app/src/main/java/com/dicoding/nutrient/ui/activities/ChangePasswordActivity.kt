package com.dicoding.nutrient.ui.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityChangePasswordBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogSaveBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupContent()
    }

    private fun setupContent() {
        binding.ivBack.setOnClickListener {
            startActivity(
                Intent(
                    this@ChangePasswordActivity,
                    DashboardWithBotNavActivity::class.java
                )
            )
        }

        binding.buttonChangePasswordActivity.setOnClickListener {
            showDialog()
        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.buttonChangePasswordActivity.setButtonEnableChangePassword(
                    etPasswordResult = binding.edChangePassword.text.toString(),
                    etPasswordResultConfirmation = binding.edLoginPasswordConfirmation.text.toString()
                )
            }

        }

        binding.edChangePassword.addTextChangedListener(textWatcher)
        binding.edLoginPasswordConfirmation.addTextChangedListener(textWatcher)
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        val dialogBinding = CustomPopupDialogSaveBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.btDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btDialogSave.setOnClickListener {
            dialog.dismiss()
        }
    }

}