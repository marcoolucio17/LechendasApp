package com.example.lechendasapp.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RadioButtonWithText(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        modifier = modifier.clickable { onClick() }
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = text)
    }
}