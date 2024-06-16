package com.dicoding.nutrient.ui.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityChangePasswordBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogSaveBinding
import com.dicoding.nutrient.ui.viewmodels.ChangePasswordViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var changePasswordViewModel: ChangePasswordViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var alertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupContent()
        initViewModel()
        setupAction()
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
                    etPasswordResultConfirmation = binding.edChangePasswordConfirmation.text.toString()
                )
            }

        }

        binding.edChangePassword.addTextChangedListener(textWatcher)
        binding.edChangePasswordConfirmation.addTextChangedListener(textWatcher)

        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(this@ChangePasswordActivity, R.color.greenApps)
            setCancelable(false)
            setTheme(R.style.DialogTheme)
        }
        alertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        alertDialog.apply {
            setTheme(R.style.DialogTheme)
            setCancelable(false)
            setConfirmClickListener { dialog ->
                dialog.dismiss()
            }
            setTitleText("Oops...")
        }
    }

    private fun setupAction(){
        binding.apply {

            buttonChangePasswordActivity.setOnClickListener {
                userPreferencesViewModel.getTokenValue().observe(this@ChangePasswordActivity){ token ->
                    changePasswordViewModel.changePassword(
                        token,
                        edChangePassword.text.toString(),
                        edChangePasswordConfirmation.text.toString()
                    ).observe(this@ChangePasswordActivity){ result ->
                        when (result){
                            is Result.Loading -> {
                                loadingDialog.show()
                            }
                            is Result.Success -> {
                                loadingDialog.dismiss()
                                Toast.makeText(this@ChangePasswordActivity, result.data.message, Toast.LENGTH_LONG).show()
                            }
                            is Result.ErrorChangePassword -> {
                                loadingDialog.dismiss()
                                alertDialog.setContentText(result.error.message.password[0])
                                alertDialog.show()
                            }
                            is Result.ServerError -> {
                                loadingDialog.dismiss()
                                alertDialog.setContentText(result.serverError)
                                alertDialog.show()
                            }
                            else -> {}
                        }
                    }
                }
            }


        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this.application)
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]
        changePasswordViewModel = ViewModelProvider(this, factory)[ChangePasswordViewModel::class.java]
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