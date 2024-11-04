package com.example.lechendasapp.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R

@Composable
fun SimpleInputBox(
    labelText: String = "",
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    singleLine: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange },
        label = { Text(labelText) },
        placeholder = { Text(placeholder, color = Color.Gray) },
        singleLine = singleLine,
        modifier = modifier
            .width(450.dp)
    )
}
