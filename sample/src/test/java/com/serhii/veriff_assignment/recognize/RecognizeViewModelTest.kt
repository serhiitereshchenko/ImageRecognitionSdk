package com.serhii.veriff_assignment.recognize

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serhii.veriff_assignment.data.repository.RecognitionRepository
import com.serhii.veriff_assignment.rules.TestCoroutineRule
import com.serhii.veriff_assignment.screens.recognize.RecognizeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecognizeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var recognizeRepository: RecognitionRepository
    private lateinit var viewModel: RecognizeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = RecognizeViewModel(recognizeRepository)
        viewModel.currentPhotoUri = Mockito.mock(Uri::class.java)
    }

    @Test
    fun `process text when image loaded`(): Unit =
        runBlocking {
            viewModel.onImageLoaded()
            verify(recognizeRepository).retrieveText(viewModel.currentPhotoUri)
        }

    @Test
    fun `not process text when image failed`(): Unit =
        runBlocking {
            viewModel.onImageLoadFailed()
            verify(recognizeRepository, never()).retrieveText(viewModel.currentPhotoUri)
        }

    @Test
    fun `process faces when image loaded`(): Unit =
        runBlocking {
            viewModel.onImageLoaded()
            verify(recognizeRepository).retrieveFaces(viewModel.currentPhotoUri)
        }

    @Test
    fun `not process faces when image failed`(): Unit =
        runBlocking {
            viewModel.onImageLoadFailed()
            verify(recognizeRepository, never()).retrieveFaces(viewModel.currentPhotoUri)
        }
}
