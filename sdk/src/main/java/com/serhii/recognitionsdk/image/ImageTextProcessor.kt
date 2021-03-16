package com.serhii.recognitionsdk.image

import android.net.Uri
import com.serhii.recognitionsdk.data.FaceRecognitionResult
import com.serhii.recognitionsdk.data.TextRecognitionResult

interface ImageTextProcessor {

    fun retrieveText(uri: Uri): TextRecognitionResult

}
