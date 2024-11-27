package com.example.lechendasapp.screens

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
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.TopBar3


@Composable
fun ConfigurationScreen(
    onBack: () -> Unit,
    currentRoute: String = "home",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditProfile: () -> Unit,
    onLogoutConfirmed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopBar3(onBack, "Configuración", false) },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onMenuClick,
                onSearch = onSearchClick,
                onSettings = onSettingsClick,
            )
        }
    ) { innerPadding ->
        ConfigurationContent(
            onEditProfile = onEditProfile,
            onLogoutConfirmed = onLogoutConfirmed,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ConfigurationContent(
    modifier: Modifier,
    onEditProfile: () -> Unit,
    onLogoutConfirmed: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showNotifications by remember { mutableStateOf(true) }

    var showContactDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 110.dp)
    ) {
//        SectionTitle("GENERAL")
//        SettingItem(title = "Editar Perfil", onClick = onEditProfile)
//
//        Spacer(
//            modifier = Modifier
//                .padding(horizontal = 14.dp, vertical = 10.dp) // Space from each end
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(Color.Black)
//        )
//
//        SettingItem(title = "Cambiar contraseña", onClick = onEditProfile)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        SectionTitle("NOTIFICACIONES")
//        SettingSwitchItem(
//            title = "Notificaciones",
//            checked = showNotifications,
//            onCheckedChange = {
//                showNotifications = it
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("ACCIONES")
        SettingItem(title = "Cerrar sesión", onClick = { showLogoutDialog = true })

        if (showLogoutDialog) {
//            LogoutConfirmationDialog(
//                onDismiss = { showLogoutDialog = false },
//                onConfirm = {
//                    showLogoutDialog = false
            onLogoutConfirmed()
//                }
//            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("CONTACTO")
        Text(
            text = "Correo: lechendas@tec.mx\n\n" +
                    "Equipo desarrollador:\n\n" +
                    "Daniel Molina\n" +
                    "Marco Lucio\n" +
                    "Kaled Enríquez\n" +
                    "Isaac Enríquez\n" +
                    "Andrés Quintanar\n",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    )
}
//
//@Composable
//fun LogoutConfirmationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Cerrar sesión") },
//        text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
//        confirmButton = {
//            TextButton(onClick = onConfirm) {
//                Text("Cerrar sesión", color = MaterialTheme.colorScheme.primary)
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancelar")
//            }
//        }
//    )
//}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Icon(
            painterResource(R.drawable.chevron_right),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun SettingSwitchItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors()
        )
    }
}


@ScreenPreviews
@Composable
fun ConfigurationScreenPreview() {
    LechendasAppTheme {
        ConfigurationScreen(
            onBack = {},
            currentRoute = "home",
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            onEditProfile = {},
            onLogoutConfirmed = {}
        )
    }
}