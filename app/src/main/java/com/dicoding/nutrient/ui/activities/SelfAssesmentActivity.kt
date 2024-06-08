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
import com.dicoding.nutrient.databinding.ActivitySelfAssesmentBinding
import com.dicoding.nutrient.ui.viewmodels.AssestmentViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory

class SelfAssesmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfAssesmentBinding
    private lateinit var assestmentViewModel: AssestmentViewModel
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var alertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfAssesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initViewModel()
        setupAction()

    }

    private fun initComponents(){
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(this@SelfAssesmentActivity, R.color.greenApps)
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

            buttonSelfAssesment.setOnClickListener {
                val height = edAssesmentHeight.text.toString().toDouble()
                val weight = edAssesmentWeight.text.toString().toDouble()

                userPreferencesViewModel.getTokenValue().observe(this@SelfAssesmentActivity){ token ->
                    assestmentViewModel.fillAssestment(token, height, weight).observe(this@SelfAssesmentActivity){ result ->
                        when(result){
                            is Result.Loading -> {
                                loadingDialog.show()
                            }
                            is Result.Success -> {
                                loadingDialog.dismiss()
                                val intent = Intent(this@SelfAssesmentActivity, DashboardWithBotNavActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                            is Result.ErrorAssestment -> {
                                loadingDialog.dismiss()
                                alertDialog.setContentText(result.error.message)
                                alertDialog.show()
                            }
                            is Result.ServerError -> {
                                loadingDialog.dismiss()
                                alertDialog.setContentText(result.serverError)
                                alertDialog.show()
                            }
                            else -> {
                                loadingDialog.dismiss()
                            }
                        }
                    }
                }
            }
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    buttonSelfAssesment.setButtonEnableAssestment(
                        etHeightResult = edAssesmentHeight.text,
                        etWeightResult = edAssesmentWeight.text
                    )
                }

            }

            edAssesmentHeight.addTextChangedListener(textWatcher)
            edAssesmentWeight.addTextChangedListener(textWatcher)
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this@SelfAssesmentActivity.application)
        assestmentViewModel = ViewModelProvider(this, factory).get(AssestmentViewModel::class.java)
        userPreferencesViewModel = ViewModelProvider(this, factory).get(UserPreferencesViewModel::class.java)
    }
}