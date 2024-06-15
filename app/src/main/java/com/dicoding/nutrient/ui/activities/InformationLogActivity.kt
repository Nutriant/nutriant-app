package com.dicoding.nutrient.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityInformationLogBinding
import com.dicoding.nutrient.ui.viewmodels.FoodViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import java.io.File

class InformationLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationLogBinding
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var alertDialog: SweetAlertDialog
    var calor: Int = 0
    var karbo: Double = 0.0
    var protein: Double = 0.0
    var sugar: Double = 0.0
    var lemak: Double = 0.0
    var name_food: String = ""
    var currentImage: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->
        if (uri != null){
            currentImage = uri
            binding.imgFood.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            getDataFromParseIntent(bundle)
        }

        initComponents()
        initViewModel()
        setupData()
        setupAction()
    }

    private fun initComponents(){
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(this@InformationLogActivity, R.color.greenApps)
            setCancelable(false)
        }
        alertDialog = SweetAlertDialog(this@InformationLogActivity, SweetAlertDialog.ERROR_TYPE)
        alertDialog.apply {
            setCancelable(false)
            setConfirmClickListener { dialog ->
                dialog.dismiss()
            }
            setTitleText("Oops...")
        }
    }

    private fun getDataFromParseIntent(bundle: Bundle){
        bundle.apply {
            calor = getInt(DATA_CALORI, 0)
            karbo = getDouble(DATA_KARBO, 0.0)
            protein = getDouble(DATA_PROTEIN, 0.0)
            sugar = getDouble(DATA_SUGAR, 0.0)
            lemak = getDouble(DATA_LEMAK, 0.0)
            name_food = getString(NAME_FOOD, "")
        }
    }

    private fun setupData(){
        binding.apply {
            edCarbo.setText(karbo.toString())
            edFat.setText(lemak.toString())
            edGlucose.setText(sugar.toString())
            edProtein.setText(protein.toString())
            edFoodAndBev.setText(name_food)
            tvCalorTotal.text = getString(R.string.caloriestotal, calor)
        }
    }

    private fun setupAction(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        binding.btSaveHistory.setOnClickListener {
//            Logic to save the history
            saveFood()
        }

        binding.imgFood.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun saveFood(){
        val carbo = binding.edCarbo.text.toString().trim().toDouble().toInt()
        val glucose = binding.edGlucose.text.toString().trim().toDouble().toInt()
        val fat = binding.edFat.text.toString().trim().toDouble().toInt()
        val protein = binding.edProtein.text.toString().trim().toDouble().toInt()
        val food_name = binding.edFoodAndBev.text.toString()

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
            foodViewModel.resultPostFood(
                token,
                food_name,
                calor,
                glucose,
                fat,
                protein,
                carbo,
                filePhoto
            ).observe(this){ result ->
                when (result){
                    is Result.Loading -> {
                        loadingDialog.show()
                    }
                    is Result.Success -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this@InformationLogActivity, result.data.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@InformationLogActivity, DashboardWithBotNavActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    is Result.ErrorPostFood -> {
                        loadingDialog.dismiss()
                        val error = result.error.message

                        if (error.fat.isNotEmpty()){
                            binding.edFat.error = error.fat[0]
                        }
                        if (error.name.isNotEmpty()){
                            binding.edFoodAndBev.error = error.name[0]
                        }
                        if (error.protein.isNotEmpty()){
                            binding.edProtein.error = error.protein[0]
                        }
                        if (error.sugar.isNotEmpty()){
                            binding.edGlucose.error = error.sugar[0]
                        }
                        if (error.carbohydrate.isNotEmpty()){
                            binding.edCarbo.error = error.carbohydrate[0]
                        }
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
        foodViewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]
    }

    companion object {
        const val NAME_FOOD = "NAME_FOOD"
        const val DATA_CALORI = "CALOR"
        const val DATA_KARBO = "KARBO"
        const val DATA_SUGAR = "SUGAR"
        const val DATA_PROTEIN = "PROTEIN"
        const val DATA_LEMAK = "LEMAK"
    }
}