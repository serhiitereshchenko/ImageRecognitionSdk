package com.serhii.recognitionsdk.image

import android.net.Uri
import com.serhii.recognitionsdk.data.FaceRecognitionResult

/**
 * Responsible for face recognition
 */
internal interface ImageFaceProcessor {

    fun retrieveFaceCount(uri: Uri): FaceRecognitionResult

}