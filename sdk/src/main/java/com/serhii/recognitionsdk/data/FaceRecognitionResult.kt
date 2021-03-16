package com.serhii.recognitionsdk.data

import java.lang.Exception

data class FaceRecognitionResult(
    var amountOfFaces: Int = 0,
    var exception: Exception? = null
) {
    init {
        require(amountOfFaces >= 0) { "Amount of faces can't be negative" }
    }
}
