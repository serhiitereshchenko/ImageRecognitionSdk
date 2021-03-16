package com.serhii.veriff_assignment.screens.recognize

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.veriff_assignment.data.repository.RecognitionRepository
import com.serhii.veriff_assignment.mvvm.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RecognizeViewModel @Inject constructor(private val recognitionRepository: RecognitionRepository) :
    ViewModel() {

    lateinit var currentPhotoUri: Uri

    // Loading indicator
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Image from camera
    private val _image = MutableLiveData<Resource<Uri>>()
    val image: LiveData<Resource<Uri>> = _image

    // Recognized text
    private val _recognizedText = MutableLiveData<Resource<String>>()
    val recognizedText: LiveData<Resource<String>> = _recognizedText

    // Recognized faces
    private val _recognizedFaces = MutableLiveData<Resource<Int>>()
    val recognizedFaces: LiveData<Resource<Int>> = _recognizedFaces

    fun onImageLoaded() {
        _image.value = Resource.Success(currentPhotoUri)
        _loading.value = true
        processText(currentPhotoUri)
        processFaces(currentPhotoUri)
    }

    fun onImageLoadFailed() {
        _image.value = Resource.Error(Exception("Failed to get image"))
    }

    private fun processText(uri: Uri) = viewModelScope.launch {
        val retrieveTextResource = recognitionRepository.retrieveText(uri)
        _recognizedText.value = retrieveTextResource
    }

    private fun processFaces(uri: Uri) = viewModelScope.launch {
        val retrieveFacesResource = recognitionRepository.retrieveFaces(uri)
        _recognizedFaces.value = retrieveFacesResource
        _loading.value = false
    }
}
