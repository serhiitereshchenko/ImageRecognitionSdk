package com.serhii.recognitionsdk.image

import android.content.Context
import android.net.Uri
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.rekognition.AmazonRekognition
import com.amazonaws.services.rekognition.AmazonRekognitionClient
import com.amazonaws.services.rekognition.model.DetectFacesRequest
import com.amazonaws.services.rekognition.model.Image
import com.serhii.recognitionsdk.data.FaceRecognitionResult
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

internal class AWSRecognitionFaceProcessor(
    private val context: Context,
    accessKey: String?,
    secretKey: String?
) : ImageFaceProcessor {

    private var amazonClient: AmazonRekognition =
        AmazonRekognitionClient(BasicAWSCredentials(accessKey, secretKey))

    override fun retrieveFaceCount(uri: Uri): FaceRecognitionResult {
        val result = FaceRecognitionResult()
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val bytesArray = readBytes(inputStream)
                val searchFacesByImageRequest =
                    DetectFacesRequest()
                        .withImage(Image().withBytes(ByteBuffer.wrap(bytesArray)))
                val response = amazonClient.detectFaces(searchFacesByImageRequest)
                result.amountOfFaces = response.faceDetails.size
            } else {
                throw IOException("Can't open input stream")
            }
        } catch (e: Exception) {
            result.exception = e
        }
        return result
    }
}

@Throws(IOException::class)
private fun readBytes(inputStream: InputStream): ByteArray {
    val byteBuffer = ByteArrayOutputStream()
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)
    var len: Int
    while (inputStream.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }
    return byteBuffer.toByteArray()
}
