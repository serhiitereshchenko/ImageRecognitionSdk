package com.serhii.veriff_assignment.data.repository

import android.net.Uri
import com.serhii.recognitionsdk.VeriffSdk
import com.serhii.veriff_assignment.di.IODispatcher
import com.serhii.veriff_assignment.mvvm.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RecognitionRepository {
    suspend fun retrieveText(uri: Uri): Resource<String>
    suspend fun retrieveFaces(uri: Uri): Resource<Int>
}

internal class RecognitionRepositoryImpl(
    private val sdk: VeriffSdk,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecognitionRepository {

    override suspend fun retrieveText(uri: Uri): Resource<String> =
        withContext(ioDispatcher) {
            val result = sdk.retrieveText(uri)
            if (result.exception != null) {
                return@withContext Resource.Error(result.exception as Exception)
            } else {
                return@withContext Resource.Success(result.text ?: "")
            }
        }

    override suspend fun retrieveFaces(uri: Uri): Resource<Int> =
        withContext(ioDispatcher) {
            val result = sdk.retrieveFaces(uri)
            if (result.exception != null) {
                return@withContext Resource.Error(result.exception as Exception)
            } else {
                return@withContext Resource.Success(result.amountOfFaces ?: 0)
            }
        }
}
