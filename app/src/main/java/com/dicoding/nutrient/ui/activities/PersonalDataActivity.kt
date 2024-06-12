package com.dicoding.nutrient.ui.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityPersonalDataBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogSaveBinding
import com.dicoding.nutrient.ui.fragments.dashboard.ProfileFragment
import com.dicoding.nutrient.ui.viewmodels.ProfileViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import com.dicoding.nutrient.utils.Gender
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PersonalDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var alertDialog: SweetAlertDialog
    var currentImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initViewModel()
        setupAction()
        setupComponent()

    }

    private fun initComponents(){
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(this@PersonalDataActivity, R.color.greenApps)
            setCancelable(false)
        }
        alertDialog = SweetAlertDialog(this@PersonalDataActivity, SweetAlertDialog.ERROR_TYPE)
        alertDialog.apply {
            setCancelable(false)
            setConfirmClickListener { dialog ->
                dialog.dismiss()
            }
            setTitleText("Oops...")
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->
        if (uri != null){
            currentImage = uri
            binding.avImage.setImageURI(uri)
        }
    }

    private fun setupAction(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, DashboardWithBotNavActivity::class.java))
        }

        binding.btSaveProfile.setOnClickListener {
            showDialog()
        }

        binding.avImage.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.edField1.setOnClickListener {
            val dateOfBirth = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    dateOfBirth[Calendar.YEAR] = year
                    dateOfBirth[Calendar.MONTH] = monthOfYear
                    dateOfBirth[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val getTime = dateOfBirth.time
                    val strFormatDefault = "yyyy-MM-dd"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    binding.edField1.setText(simpleDateFormat.format(getTime))
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

        binding.edField2.setOnClickListener {
            val popMenu = PopupMenu(this@PersonalDataActivity, binding.edField2)
            popMenu.menuInflater.inflate(R.menu.gender_menu, popMenu.menu)
            popMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                @SuppressLint("SetTextI18n")
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    when (p0!!.itemId){
                        R.id.male -> {
                            binding.edField2.setText("Male")
                        }
                        R.id.female -> {
                            binding.edField2.setText("Female")
                        }
                        else -> {

                        }
                    }
                    return true
                }
            })
            popMenu.show()
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

        dialogBinding.btDialogSave.setOnClickListener {
            saveProfile()
            dialog.dismiss()
        }
    }

    private fun saveProfile(){
        val username = binding.edPersonaldataUsername.text.toString()
        val birthdate = binding.edField1.text.toString()
        val gender = Gender.valueOf(binding.edField2.text.toString()).genderValue
        val height = binding.edPersonaldataHeight.text.toString().trim().toInt()
        val weight = binding.edPersonaldataWeight.text.toString().trim().toInt()
        val _method = "PUT"

        var filePhoto: File? = null
        if (currentImage != null){
            val inputStream = this.contentResolver.openInputStream(currentImage!!)
            val cursor = this.contentResolver.query(currentImage!!, null, null, null, null)
            cursor?.use { c ->
                val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (c.moveToFirst()){
                    val name = c.getString(nameIndex)
                    inputStream?.let { inputStream ->
                        val file = File(this.cacheDir, name)
                        val os = file.outputStream()
                        os.use {
                            inputStream.copyTo(it)
                        }

                        filePhoto = file
                    }
                }
            }
        }
        userPreferencesViewModel.getTokenValue().observe(this){ token ->
            profileViewModel.updateProfile(
                token,
                username,
                birthdate,
                gender,
                height,
                weight,
                filePhoto,
                _method
            )
            profileViewModel.resultUpdateProfile.observe(this){ result ->
                when (result){
                    is Result.Loading -> {
                        loadingDialog.show()
                    }
                    is Result.Success -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this, result.data.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PersonalDataActivity, DashboardWithBotNavActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
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

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this.application)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        userPreferencesViewModel = ViewModelProvider(this, factory).get(UserPreferencesViewModel::class.java)

    }

    private fun setupComponent(){
        userPreferencesViewModel.getTokenValue().observe(this){ token ->
            profileViewModel.getAllMyProfile(token)
            profileViewModel.resultAllMyProfile.observe(this){ result ->
                when (result){
                    is Result.Loading -> {
                        binding.loadingProfil.visibility = View.VISIBLE
                        binding.layoutFormProfil.visibility = View.GONE
                    }
                    is Result.Success -> {
                        binding.loadingProfil.visibility = View.GONE
                        binding.layoutFormProfil.visibility = View.VISIBLE

                        val dataProfile = result.data.data
                        val user_data = result.data.data.user_data
                        binding.edPersonaldataEmail.setText(dataProfile.email)
                        binding.edPersonaldataUsername.setText(dataProfile.username)
                        binding.edPersonaldataHeight.setText(user_data.height.toString())
                        binding.edPersonaldataWeight.setText(user_data.weight.toString())
                        binding.edField1.setText(user_data.birthdate)
                        binding.edField2.setText(user_data.gender)
                        Glide.with(this@PersonalDataActivity)
                            .load(user_data.image)
                            .apply(RequestOptions().placeholder(R.drawable.avatar_dummy).fitCenter())
                            .into(binding.avImage)
                        Log.d("PersonalDataActivity", user_data.birthdate)
                    }
                    is Result.ServerError -> {
                        Toast.makeText(this@PersonalDataActivity, result.serverError, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }
}