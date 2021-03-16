package com.serhii.recognitionsdk

import android.content.Context
import android.text.TextUtils
import com.google.mlkit.vision.text.TextRecognition
import com.serhii.recognitionsdk.image.AWSRecognitionFaceProcessor
import com.serhii.recognitionsdk.image.MlKitImageTextProcessor
import java.lang.IllegalArgumentException

/**
 * Builder class that should be used to create {@link com.serhii.recognitionsdk.VeriffSdk} instance
 */
class SdkBuilder {

    /**
     * Application context
     */
    var context: Context? = null
        private set

    /**
     * AWS access key
     * @see <a href="https://docs.aws.amazon.com/rekognition/latest/dg/setting-up.html">How to retrieve AWS credentials</a>
     */
    var awsAccessKey: String? = null
        private set

    /**
     * AWS secret key
     * @see <a href="https://docs.aws.amazon.com/rekognition/latest/dg/setting-up.html">How to retrieve AWS credentials</a>
     */
    var awsSecretKey: String? = null
        private set

    fun context(context: Context): SdkBuilder = apply { this.context = context }

    fun awsCredentials(apiKey: String, secretKey: String): SdkBuilder = apply {
        this.awsAccessKey = apiKey
        this.awsSecretKey = secretKey
    }

    fun build(): VeriffSdk {
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
