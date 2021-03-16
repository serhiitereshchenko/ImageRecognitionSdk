package com.serhii.recognitionsdk.data

import java.lang.Exception

data class TextRecognitionResult(
    var text: String? = null,
    var exception: Exception? = null
)
