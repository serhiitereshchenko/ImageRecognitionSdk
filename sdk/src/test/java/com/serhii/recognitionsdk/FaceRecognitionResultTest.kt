package com.serhii.recognitionsdk

import com.serhii.recognitionsdk.data.FaceRecognitionResult
import org.junit.Test

class FaceRecognitionResultTest {

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when faces amount is negative`() {
        FaceRecognitionResult(amountOfFaces = -1)
    }
}
