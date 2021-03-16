package com.serhii.recognitionsdk.data

import java.lang.Exception

/**
 * @param text The recognised text
 * @param exception Exception if something went wrong
 */
data class TextRecognitionResult(
    var text: String? = null,
    var exception: Exception? = null
)
