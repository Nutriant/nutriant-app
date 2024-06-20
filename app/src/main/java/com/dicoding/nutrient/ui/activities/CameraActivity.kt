package com.dicoding.nutrient.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityCameraBinding
import com.dicoding.nutrient.ml.ObjectDetectionHelper
import com.dicoding.nutrient.ml.TextRecognitionProcessor
import com.google.mlkit.vision.text.Text
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private var currentImageUri: Uri? = null
    private var isFlashlightOn: Boolean = false
    private var camera: androidx.camera.core.Camera? = null
    private lateinit var objectDetectionHelper: ObjectDetectionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        objectDetectionHelper = ObjectDetectionHelper(this)

        // Request permissions
        if (allPermissionsGranted()) {
            startCameraX()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.ivBackNavs.setOnClickListener {
            finish()
        }

        binding.fabGallery.setOnClickListener {
            startGallery()
        }

        binding.fabFlashlight.setOnClickListener {
            toggleFlashLight()
        }

        binding.fabCapture.setOnClickListener {
            takePhoto()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCameraX()
            } else {
                showToast("Permissions not granted by the user.")
                finish()
            }
        }
    }

    private fun startCameraX() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().setFlashMode(ImageCapture.FLASH_MODE_OFF).build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            externalMediaDirs.firstOrNull(),
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val message = "Photo capture succeeded: $savedUri"
                    showToast(message)
                    Log.d(TAG, message)
                    processImage(photoFile.absolutePath)
                }
            }
        )
    }

    private fun processImage(imagePath: String) {
        val bitmap = BitmapFactory.decodeFile(imagePath)

//         Run object detection
        val detections = objectDetectionHelper.detectObjects(bitmap)

        // Draw bounding boxes
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 8f
            color = Color.RED
        }

        for (detection in detections) {
            if (detection.score > 0.5) {
                val boundingBox = detection.boundingBox
                val rect = RectF(
                    boundingBox[1] * bitmap.width,
                    boundingBox[0] * bitmap.height,
                    boundingBox[3] * bitmap.width,
                    boundingBox[2] * bitmap.height
                )
                canvas.drawRect(rect, paint)
            }
        }

        // OCR processing
        val textRecognitionProcessor = TextRecognitionProcessor()
        textRecognitionProcessor.recognizeText(bitmap, { visionText ->
            val extractedText = extractLeftToRightText(visionText).joinToString(" ")
//            val getRegexMap = extractedText.GetDataScanNutrition()
            Log.d(TAG, "Extracted Text: ${extractedText.trimIndent()}")
//            Log.d("MAP", getRegexMap.get("Lemak").toString())

            val intent = Intent(this, InformationLogScanActivity::class.java).apply {
                putExtra("EXTRACTED_TEXT", extractedText)
            }
            startActivity(intent)
        }, { e ->
            Log.e(TAG, "Text recognition failed: ${e.message}", e)
            showToast("Text recognition failed: ${e.message}")
        })
    }

    fun extractLeftToRightText(visionText: Text): List<String> {
        val horizontalText = mutableListOf<String>()

        // Gather all lines of text
        val lines = mutableListOf<Text.Line>()
        for (block in visionText.textBlocks) {
            lines.addAll(block.lines)
        }

        // Sort lines by their vertical position (top coordinate)
        lines.sortBy { it.boundingBox?.top }

        // Process each line
        for (line in lines) {
            horizontalText.add(correctOcrErrors(line.text))
        }

        return horizontalText
    }

    private fun correctOcrErrors(text: String): String {
        // Define a regex pattern to identify common errors where '9' should be 'g'
        val pattern = Regex("(\\d)9\\b")  // Look for a digit followed by '9' at the end of a word

        // Replace '9' with 'g'
        val correctedText = pattern.replace(text) { result ->
            val digit = result.groupValues[1]
            "$digit"
        }

        return correctedText
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun toggleFlashLight() {
        if (!isFlashlightOn) {
            turnOnFlashlight()
            binding.fabFlashlight.setImageResource(R.drawable.flashlight_svgrepo_com_yellow)
            showToast(getString(R.string.flashlight_on))
        } else {
            turnOffFlashlight()
            binding.fabFlashlight.setImageResource(R.drawable.flashlight_svgrepo_com)
            showToast(getString(R.string.flashlight_off))
        }
        binding.fabFlashlight.invalidate()
    }

    private fun turnOnFlashlight() {
        camera?.cameraControl?.enableTorch(true)
        isFlashlightOn = true
    }

    private fun turnOffFlashlight() {
        camera?.cameraControl?.enableTorch(false)
        isFlashlightOn = false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "CameraX"
        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
