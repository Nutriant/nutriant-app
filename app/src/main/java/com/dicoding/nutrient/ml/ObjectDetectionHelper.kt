package com.dicoding.nutrient.ml

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ObjectDetectionHelper(context: Context) {
    private val interpreter: Interpreter

    init {
        val model = FileUtil.loadMappedFile(context, "modelnewbanget.tflite")
        interpreter = Interpreter(model)

        // Debug: Print input tensor shape and type
        val inputTensorShape = interpreter.getInputTensor(0).shape()
        val inputTensorType = interpreter.getInputTensor(0).dataType()
        Log.d(
            "ModelInputShape",
            "Shape: ${inputTensorShape.joinToString()}, Type: $inputTensorType"
        )
    }

    fun detectObjects(image: Bitmap): List<DetectionResult> {
        val inputTensorShape = interpreter.getInputTensor(0).shape()
        val height = inputTensorShape[1]
        val width = inputTensorShape[2]
        val channels = inputTensorShape[3]

        val resizedImage = Bitmap.createScaledBitmap(image, width, height, true)

        // Correct buffer size allocation
        val inputBuffer = ByteBuffer.allocateDirect(4 * width * height * channels) // 4 bytes per float
        inputBuffer.order(ByteOrder.nativeOrder())

        // Convert the image to ByteBuffer
        val intValues = IntArray(width * height)
        resizedImage.getPixels(intValues, 0, width, 0, 0, width, height)
        var pixel = 0
        for (i in 0 until height) {
            for (j in 0 until width) {
                val value = intValues[pixel++]
                inputBuffer.putFloat(((value shr 16) and 0xFF) / 255.0f)
                inputBuffer.putFloat(((value shr 8) and 0xFF) / 255.0f)
                inputBuffer.putFloat((value and 0xFF) / 255.0f)
            }
        }

        val outputBoxes = TensorBuffer.createFixedSize(intArrayOf(1, 100, 4), DataType.FLOAT32)
        val outputScores = TensorBuffer.createFixedSize(intArrayOf(1, 100), DataType.FLOAT32)
        val outputClasses = TensorBuffer.createFixedSize(intArrayOf(1, 100), DataType.FLOAT32)
        val numDetections = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)

        val outputMap = mapOf(
            0 to outputBoxes.buffer,
            1 to outputScores.buffer,
            2 to outputClasses.buffer,
            3 to numDetections.buffer
        )
        interpreter.runForMultipleInputsOutputs(arrayOf(inputBuffer), outputMap)

        val detections = mutableListOf<DetectionResult>()
        val numDetectionsValue = numDetections.floatArray[0].toInt()
        for (i in 0 until numDetectionsValue) {
            val boundingBox = outputBoxes.floatArray.copyOfRange(i * 4, (i + 1) * 4)
            val score = outputScores.floatArray[i]
            val classId = outputClasses.floatArray[i]
            detections.add(DetectionResult(boundingBox, score, classId))
        }
        return detections
    }
}

data class DetectionResult(
    val boundingBox: FloatArray,
    val score: Float,
    val classId: Float
)
