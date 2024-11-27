package com.example.lechendasapp.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.lechendasapp.MainActivity
import com.example.lechendasapp.viewmodels.CameraViewModel

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.lechendasapp.R

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CameraPreview(
    activity: MainActivity,
    onBack: () -> Unit,
    cameraViewModel: CameraViewModel = hiltViewModel()) {
    val imageCapture = remember { ImageCapture.Builder().build() }
    val showModal = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    // val userInput = remember { mutableStateOf("") }

    val controller = remember {
        LifecycleCameraController(
            activity
        ).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    cameraViewModel.setImageCapture(controller)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .clickable {
                        if ((activity as MainActivity).arePermissionsGranted()) {
                            cameraViewModel.takePhoto2(
                                context = activity,
                                onImageSaved = { file ->
                                    println("Photo saved at: ${file.absolutePath}")
                                    // Show modal when photo is taken
                                    showModal.value = true
                                },
                                onError = { exception ->
                                    println("Error saving photo: ${exception.message}")
                                }
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Take Photo",
                    modifier = Modifier.size(40.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
        }
    }

    if (showModal.value) {
        AlertDialog(
            onDismissRequest = {
                showModal.value = false
                onBack() },
            confirmButton = {
                Button(onClick = {
                    showModal.value = false
                    onBack()
                }) {
                    Text(text = "Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showModal.value = false
                }) {
                    Text(text = "Tomar otra foto")
                }
            },
            title = { Text(text = "Imagen capturada con éxito") },
            text = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "¡Se guardó la imagen! Puedes tomar otra foto, o continuar con el formulario.")
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
}
