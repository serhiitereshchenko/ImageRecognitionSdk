package com.serhii.recognitionsdk

import android.content.Context
import android.text.TextUtils
import com.google.mlkit.vision.text.TextRecognition
import com.serhii.recognitionsdk.image.AWSRecognitionFaceProcessor
import com.serhii.recognitionsdk.image.MlKitImageTextProcessor
import java.lang.IllegalArgumentException

class SdkBuilder {

    var context: Context? = null
        private set
    var awsAccessKey: String? = null
        private set
    var awsSecretKey: String? = null
        private set

    fun context(context: Context): SdkBuilder = apply { this.context = context }

    fun awsCredentials(apiKey: String, secretKey: String): SdkBuilder = apply {
        this.awsAccessKey = apiKey
        this.awsSecretKey = secretKey
    }

    fun build(): VeriffSdk {
        if (context == null) throw IllegalArgumentException("Context must not be null")
        if (awsAccessKey.isNullOrEmpty() || awsAccessKey.isNullOrEmpty()) throw IllegalArgumentException(
            "AWS credentials must be valid"
        )

        context?.let {
            return VeriffSdkImpl(
                MlKitImageTextProcessor(it, TextRecognition.getClient()),
                AWSRecognitionFaceProcessor(it, awsAccessKey, awsSecretKey)
            )
        }
        throw IllegalArgumentException("Context should be initialized")
    }
}
