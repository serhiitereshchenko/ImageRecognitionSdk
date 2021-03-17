package com.serhii.recognitionsdk.data

import org.junit.Test

class FaceRecognitionResultTest {

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when faces amount is negative`() {
        FaceRecognitionResult(amountOfFaces = -1)
    }
}
