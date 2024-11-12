package com.example.lechendasapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.lechendasapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar3(
    onBack: () -> Unit,
    title: String = "Búsqueda"
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack, contentDescription = null
                )
            }
        },
        title = { Text(text = title) },
        actions = { // Add the actions parameter for additional icons
            IconButton(onClick = {/*TODO*/}) { // Set up onClick for the menu icon
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = "More options"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    )
}