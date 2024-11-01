package com.example.lechendasapp.Views

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
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
import com.example.lechendasapp.utils.TopBar2
import com.example.lechendasapp.utils.TopBar3
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.example.lechendasapp.R

@Composable
fun ConfigurationScreen(
    onBack: () -> Unit,
    currentRoute: String = "home",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
)  {
    Scaffold(
    topBar = { TopBar3(onBack, "Configuración") },
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ConfigurationContent(modifier: Modifier) {
    Column(modifier = Modifier.fillMaxSize().padding(top = 110.dp)) {
        SectionTitle("GENERAL")
        SettingItem(title = "Editar Perfil", onClick = { /* handle action */ })

        Spacer(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp) // Space from each end
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        SettingItem(title = "Cambiar contraseña", onClick = { /* handle action */ })

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("NOTIFICACIONES")
        SettingSwitchItem(
            title = "Notificaciones",
            checked = true,
            onCheckedChange = { /* handle switch action */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Actions Section
        SectionTitle("ACCIONES")
        SettingItem(title = "Cerrar sesión", onClick = { /* handle action */ })
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
        Icon(painterResource(R.drawable.chevron_right), contentDescription = null, modifier = Modifier.size(40.dp))
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
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors())
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
            onSettingsClick = {}
        )
    }
}