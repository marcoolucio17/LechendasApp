package com.example.lechendasapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.TopBar3
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.R

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
)  {
    Scaffold(
        topBar = { TopBar3(onBack, "", false, false) },

    ) { innerPadding ->
        EditProfileContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EditProfileContent(modifier: Modifier) {
    var name by remember { mutableStateOf(TextFieldValue("Samantha Smith")) }
    var email by remember { mutableStateOf(TextFieldValue("samanthasmith@gmail.com")) }
    var password by remember { mutableStateOf(TextFieldValue("Samantha Smith")) }
    var phone by remember { mutableStateOf(TextFieldValue("+57 312 345 6789")) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 100.dp, horizontal = 40.dp).fillMaxWidth()
    ) {

        Box(
            contentAlignment = Alignment.BottomEnd,

            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)

        ) {

            Image(
                painter = painterResource(id = R.drawable.noprofile),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize().clip(CircleShape)
            )

            IconButton(
                onClick = { /* Handle profile picture change */ },
                modifier = Modifier
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit Profile Picture",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields
        ProfileInputField(label = "Nombre", value = name, onValueChange = { name = it })
        ProfileInputField(label = "Email", value = email, onValueChange = { email = it })
        ProfileInputField(label = "Contraseña", value = password, onValueChange = { password = it })
        ProfileInputField(label = "Teléfono", value = phone, onValueChange = { phone = it })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Handle save action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "GUARDAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }

    }
}

@Composable
fun ProfileInputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@ScreenPreviews
@Composable
fun EditProfileScreenPreview() {
    LechendasAppTheme {
        EditProfileScreen(
            onBack = {},
        )
    }
}