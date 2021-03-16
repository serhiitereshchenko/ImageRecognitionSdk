package com.serhii.recognitionsdk.image

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import com.serhii.recognitionsdk.data.TextRecognitionResult
import timber.log.Timber
import java.io.IOException

internal class MlKitImageTextProcessor(
    private val context: Context,
    private val textRecognizer: TextRecognizer
) : ImageTextProcessor {

    override fun retrieveText(uri: Uri): TextRecognitionResult {
        val result = TextRecognitionResult()
        try {
            val image = InputImage.fromFilePath(context, uri)
            val data = Tasks.await(textRecognizer.process(image))
            result.text = data.text
        } catch (e: IOException) {
            Timber.e(e)
        }
        return result
    }
}
