package com.serhii.veriff_assignment.recognize

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.serhii.recognitionsdk.VeriffSdk
import com.serhii.recognitionsdk.data.FaceRecognitionResult
import com.serhii.recognitionsdk.data.TextRecognitionResult
import com.serhii.veriff_assignment.data.repository.RecognitionRepository
import com.serhii.veriff_assignment.data.repository.RecognitionRepositoryImpl
import com.serhii.veriff_assignment.mvvm.Resource
import com.serhii.veriff_assignment.rules.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecognitionRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var veriffSdk: VeriffSdk
    private lateinit var recognitionRepository: RecognitionRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        recognitionRepository = RecognitionRepositoryImpl(veriffSdk)
    }

    @Test
    fun `return error on process faces when sdk throws exception`(): Unit =
        runBlocking {
            val uri = Mockito.mock(Uri::class.java)
            given(veriffSdk.retrieveFaces(uri)).willReturn(FaceRecognitionResult(exception = Exception()))
            assert(recognitionRepository.retrieveFaces(uri) is Resource.Error)
        }

    @Test
    fun `return success on process faces when sdk returns result`(): Unit =
        runBlocking {
            val uri = Mockito.mock(Uri::class.java)
            given(veriffSdk.retrieveFaces(uri)).willReturn(FaceRecognitionResult())
            assert(recognitionRepository.retrieveFaces(uri) is Resource.Success)
        }

    @Test
    fun `return error on process text when sdk throws exception`(): Unit =
        runBlocking {
            val uri = Mockito.mock(Uri::class.java)
            given(veriffSdk.retrieveText(uri)).willReturn(TextRecognitionResult(exception = Exception()))
            assert(recognitionRepository.retrieveText(uri) is Resource.Error)
        }

    @Test
    fun `return success on process text when sdk returns result`(): Unit =
        runBlocking {
            val uri = Mockito.mock(Uri::class.java)
            given(veriffSdk.retrieveText(uri)).willReturn(TextRecognitionResult())
            assert(recognitionRepository.retrieveText(uri) is Resource.Success)
        }
}
