package com.dicoding.nutrient.ui.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityRegisterBinding
import com.dicoding.nutrient.ui.viewmodels.RegisterViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import com.dicoding.nutrient.utils.Gender
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var infoDialog: SweetAlertDialog
    var selectedGender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initViewModel()

        binding.apply {
            buttonRegisterActivity.setOnClickListener {
                registerViewModel.register(
                    username = edRegisterUsername.text.toString(),
                    email = edRegisterEmail.text.toString(),
                    password = edRegisterPassword.text.toString(),
                    password_confirm = edRegisterPasswordConfirm.text.toString(),
                    birthdate = edRegisterDob.text.toString(),
                    gender = Gender.valueOf(selectedGender).genderValue
                ).observe(this@RegisterActivity) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    loadingDialog.show()
                                }

                                is Result.Success -> {
                                    loadingDialog.dismiss()
                                    infoDialog.show()
                                }

                                is Result.ErrorRegister -> {
                                    loadingDialog.dismiss()
                                    val error = result.error.message
                                    if (error.username.isNotEmpty()) {
                                        edRegisterUsername.error = error.username[0]
                                    }
                                    if (error.email.isNotEmpty()) {
                                        edRegisterEmail.error = error.email[0]
                                    }
                                    if (error.birthdate.isNotEmpty()) {
                                        edRegisterDob.error = error.birthdate[0]
                                    }
                                    if (error.password.isNotEmpty()) {
                                        edRegisterPassword.error = error.password[0]
                                    }
                                    if (error.password_confirmation.isNotEmpty()) {
                                        edRegisterPasswordConfirm.error =
                                            error.password_confirmation[0]
                                    }
                                    if (error.gender.isNotEmpty()) {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            error.gender[0],
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                is Result.ServerError -> {
                                    loadingDialog.dismiss()
                                    Log.d("RegisterActivity", result.serverError)
                                }

                                else -> {
                                    loadingDialog.dismiss()
                                }
                            }
                        }
                    }
            }
            tvLoginNow.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }

    }

    private fun initComponents() {
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor =
                ContextCompat.getColor(this@RegisterActivity, R.color.greenApps)
            setCancelable(false)
            setTheme(R.style.DialogTheme)
        }

        infoDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        infoDialog.apply {
            setTitleText(getString(R.string.success))
            setContentText(getString(R.string.account_created))
            setConfirmText(getString(R.string.alright))
            setConfirmClickListener {
                finish()
            }
            setTheme(R.style.DialogTheme)
        }

        val spinner: Spinner = findViewById(R.id.spinnerGender)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                val selected: String = p0?.getItemAtPosition(p2)?.toString() ?: ""
                selectedGender = if (p2 == 1){
                    Gender.valueOf(Gender.fromInt(p2-1).toString()).toString()
                } else {
                    Gender.valueOf(Gender.fromInt(p2+1).toString()).toString()
                }
                Log.d("RegisterActivity", selectedGender)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.edRegisterDob.setOnClickListener {
            val dateOfBirth = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    dateOfBirth[Calendar.YEAR] = year
                    dateOfBirth[Calendar.MONTH] = monthOfYear
                    dateOfBirth[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val getTime = dateOfBirth.time
                    val strFormatDefault = "yyyy-MM-dd"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    binding.edRegisterDob.setText(simpleDateFormat.format(getTime))
                }
            val datePickerDialog = DatePickerDialog(
                this, R.style.DatePickerDialogTheme, date,
                dateOfBirth[Calendar.YEAR],
                dateOfBirth[Calendar.MONTH],
                dateOfBirth[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.setOnShowListener {
                val positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                positiveButton.setTextColor(Color.BLACK)

                val negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                negativeButton.setTextColor(Color.BLACK)
            }
            datePickerDialog.show()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.apply {
                    buttonRegisterActivity.setButtonEnableRegister(
                        etEmailResult = edRegisterEmail.text.toString(),
                        etNameResult = edRegisterUsername.text.toString(),
                        etPasswordResult = edRegisterPassword.text.toString()
                    )
                }
            }

        }

        binding.edRegisterEmail.addTextChangedListener(textWatcher)
        binding.edRegisterUsername.addTextChangedListener(textWatcher)
        binding.edRegisterPassword.addTextChangedListener(textWatcher)
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this@RegisterActivity.application)
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }
}