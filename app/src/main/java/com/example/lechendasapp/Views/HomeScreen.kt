package com.example.lechendasapp.Views

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.NavigationBar


@Composable
fun HomeScreen(
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = { CustomBoxLayout() },
        bottomBar = {
            NavigationBar {
                // Ejemplo de contenido de la barra de navegación
                IconButton(onClick = { /* Acción para el primer botón */ }) {
                    Icon(Icons.Filled.Home, contentDescription = "Inicio")
                }
                IconButton(onClick = { /* Acción para el segundo botón */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                }
                IconButton(onClick = { /* Acción para el tercer botón */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configuraciones")
                }
            }
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CustomBoxLayout(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.height(dimensionResource(R.dimen.top_bar_height))

    ) {
        Image(
            painter = painterResource(id = R.drawable.semicircle),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)

        ){

            Image(
                painter = painterResource(id = R.drawable.capybara),
                contentDescription = null,
                modifier = Modifier
                .size(40.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.profilepicture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text("Hola, tilín",
                style =  MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),)
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(y = 20.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.BottomCenter),

            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier
){
}



@ScreenPreviews
@Composable
fun HomeScreenPreview() {
    LechendasAppTheme {
        HomeScreen(
            onBack = {},
        )
    }
}