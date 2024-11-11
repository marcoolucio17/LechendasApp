package com.example.lechendasapp.viewmodels

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import androidx.core.content.ContextCompat
import java.io.File

class CameraViewModel : ViewModel() {
    private var imageCapture: ImageCapture? = null

    fun setImageCapture(imageCapture: ImageCapture) {
        this.imageCapture = imageCapture
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun takePhoto(
        context: Context,
        saveToMediaStore: Boolean,
        onImageSaved: (File) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        if (imageCapture == null) {
            Log.e("CameraViewModel", "ImageCapture is not initialized.")
            return
        }

        if (saveToMediaStore) {
            savePhotoToMediaStore(context, onImageSaved, onError)
        } else {
            savePhotoToFile(context, onImageSaved, onError)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun savePhotoToMediaStore(
        context: Context,
        onImageSaved: (File) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraExample")
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (imageUri != null) {
            val outputOptions = ImageCapture.OutputFileOptions.Builder(
                resolver.openOutputStream(imageUri)!!
            ).build()

            imageCapture?.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        onImageSaved(File(imageUri.path ?: ""))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        onError(exception)
                    }
                }
            )
        } else {
            Log.e("CameraViewModel", "Failed to create image URI")
        }
    }

    private fun savePhotoToFile(
        context: Context,
        onImageSaved: (File) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val photoFile = File(
            context.getExternalFilesDir(null),
            "IMG_${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onImageSaved(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }
}