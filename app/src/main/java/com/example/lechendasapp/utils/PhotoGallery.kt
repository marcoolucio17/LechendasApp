package com.example.lechendasapp.utils

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.viewmodels.PhotoGalleryViewModel
import kotlin.collections.forEach


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotoGallery(
    unassociatedPhotos: List<Photo>,
    associatedPhotos: List<Photo>,
    viewModel: PhotoGalleryViewModel = hiltViewModel()
) {
    Column {
        if (unassociatedPhotos.isNotEmpty()) {
            Text("Unassociated Photos", modifier = Modifier.padding(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                unassociatedPhotos.forEach { photo ->
                    val mediaId = photo.filePath.substringAfterLast("/").toLong()
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mediaId
                    )
                    Box(
                        modifier = Modifier.size(100.dp)
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = contentUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    viewModel.onImageSelected(contentUri.toString())
                                }
                        )

                        // Cross icon to delete image
                        IconButton(
                            onClick = {
                                viewModel.onDeleteConfirmationRequested(photo)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .padding(4.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.White,
                                modifier = Modifier.background(MaterialTheme.colorScheme.error, shape = RoundedCornerShape(2.dp)))
                        }
                    }
                }
            }
        }

        if (associatedPhotos.isNotEmpty()) {
            Text("Associated Photos", modifier = Modifier.padding(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                associatedPhotos.forEach { photo ->
                    val mediaId = photo.filePath.substringAfterLast("/").toLong()
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mediaId
                    )
                    Box(
                        modifier = Modifier.size(100.dp)
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = contentUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    viewModel.onImageSelected(contentUri.toString())
                                }
                        )

                        // Cross icon to delete image
                        IconButton(
                            onClick = {
                                viewModel.onDeleteConfirmationRequested(photo)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .padding(4.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.White,
                                modifier = Modifier.background(MaterialTheme.colorScheme.error, shape = RoundedCornerShape(2.dp)))
                        }
                    }
                }
            }
        }

        if (viewModel.isFullScreen.value && viewModel.selectedImageUri.value != null) {
            Dialog(onDismissRequest = { viewModel.onDismissFullScreen() }) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = viewModel.selectedImageUri.value,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        if (viewModel.showDeleteConfirmation.value && viewModel.imageToDelete.value != null) {
            AlertDialog(
                onDismissRequest = { viewModel.onDeleteCancelled() },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this image?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onDeleteConfirmed()
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.onDeleteCancelled() }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}