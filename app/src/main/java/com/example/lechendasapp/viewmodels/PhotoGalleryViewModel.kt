package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val _isFullScreen = mutableStateOf(false)
    val isFullScreen: State<Boolean> = _isFullScreen

    val _selectedImageUri = mutableStateOf<String?>(null)
    val selectedImageUri: State<String?> = _selectedImageUri

    val _showDeleteConfirmation = mutableStateOf(false)
    val showDeleteConfirmation: State<Boolean> = _showDeleteConfirmation

    val _imageToDelete = mutableStateOf<Photo?>(null)
    val imageToDelete: State<Photo?> = _imageToDelete


    fun deleteImage(photo: Photo) {
        viewModelScope.launch {
            photoRepository.deletePhoto(photo)
        }
    }

    fun onImageSelected(uri: String) {
        _selectedImageUri.value = uri
        _isFullScreen.value = true
    }

    fun onDeleteConfirmationRequested(photo: Photo) {
        _imageToDelete.value = photo
        _showDeleteConfirmation.value = true
    }

    fun onDeleteConfirmed() {
        _imageToDelete.value?.let {
            deleteImage(it)
            _showDeleteConfirmation.value = false
        }
        _imageToDelete.value = null
    }

    fun onDeleteCancelled() {
        _showDeleteConfirmation.value = false
        _imageToDelete.value = null
    }

    fun onDismissFullScreen() {
        _isFullScreen.value = false
        _selectedImageUri.value = null
    }

}