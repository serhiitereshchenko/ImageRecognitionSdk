package com.serhii.recognitionsdk.image

import android.net.Uri
import com.serhii.recognitionsdk.data.FaceRecognitionResult

interface ImageFaceProcessor {

    fun retrieveFaceCount(uri: Uri): FaceRecognitionResult

}