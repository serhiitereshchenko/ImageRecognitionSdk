package com.serhii.recognitionsdk

import android.net.Uri
import com.serhii.recognitionsdk.data.FaceRecognitionResult
import com.serhii.recognitionsdk.data.TextRecognitionResult
import com.serhii.recognitionsdk.image.ImageFaceProcessor
import com.serhii.recognitionsdk.image.ImageTextProcessor

interface VeriffSdk {

    fun retrieveText(uri: Uri): TextRecognitionResult

    fun retrieveFaces(uri: Uri): FaceRecognitionResult
}

internal class VeriffSdkImpl(
    private val imageTextProcessor: ImageTextProcessor,
    private val imageFaceProcessor: ImageFaceProcessor
) : VeriffSdk {

    override fun retrieveText(uri: Uri): TextRecognitionResult {
        return imageTextProcessor.retrieveText(uri)
    }

    override fun retrieveFaces(uri: Uri): FaceRecognitionResult {
        return imageFaceProcessor.retrieveFaceCount(uri)
    }
}
