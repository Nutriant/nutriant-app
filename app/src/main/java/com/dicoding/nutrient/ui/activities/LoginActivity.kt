package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityLoginBinding
import com.dicoding.nutrient.ui.viewmodels.LoginViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var alertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initViewModel()
        setupAction()
    }

    private fun initComponents(){
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(this@LoginActivity, R.color.greenApps)
            setCancelable(false)
            setTheme(R.style.DialogTheme)
        }
        alertDialog = SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
        alertDialog.setCancelable(false)
        alertDialog.setConfirmClickListener { dialog ->
            dialog.dismiss()
        }
        alertDialog.setTitleText("Oops...")
    }

    private fun setupAction() {
        binding.apply {
            buttonLoginActivity.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()
                loginViewModel.login(email, password).observe(this@LoginActivity){ result ->
                    when(result){
                        is Result.Loading -> {
                            loadingDialog.show()
                        }
                        is Result.Success -> {
                            loadingDialog.dismiss()
                            if (result.data.new_user == 1){
                                val intent = Intent(this@LoginActivity, SelfAssesmentActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                val intent = Intent(this@LoginActivity, DashboardWithBotNavActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }
                        is Result.ErrorLogin -> {
                            loadingDialog.dismiss()
                            alertDialog.setContentText(result.errorLogin.message)
                            alertDialog.show()
                        }
                        else -> {
                            loadingDialog.dismiss()
                        }
                    }
                }
            }
            tvRegisterNow.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    buttonLoginActivity.setButtonEnableLogin(
                        etEmailResult = edLoginEmail.text.toString(),
                        etPasswordResult = edLoginPassword.text.toString()
                    )
                }

            }

            edLoginEmail.addTextChangedListener(textWatcher)
            edLoginPassword.addTextChangedListener(textWatcher)
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this@LoginActivity.application)
        loginViewModel = ViewModelProvider(this@LoginActivity, factory).get(LoginViewModel::class.java)
    }
}