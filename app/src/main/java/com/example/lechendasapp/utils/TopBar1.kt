package com.example.lechendasapp.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.ui.theme.LechendasAppTheme

@Composable
fun TopBar1(
    onBack: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Box (modifier = Modifier.height(dimensionResource(R.dimen.top_bar_height))) {
        // Decoración extra en el lado derecho
        Image(
            painter = painterResource(R.drawable.vector_1svg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .height(180.dp)
                .align(Alignment.TopEnd)
        )
        // Primera capa de fondo (forma de ola)
        Image(
            painter = painterResource(R.drawable.vector_4svg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
        )
        // Segunda capa de fondo (segunda ola)
        Image(
            painter = painterResource(R.drawable.vector_2svg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp)) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = stringResource(title), //TODO: Cambiar a string resource
                color = Color.White,
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBar1Preview() {
    LechendasAppTheme {
        TopBar1(onBack = {}, title = R.string.bienvenido)
    }
}