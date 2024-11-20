package com.example.lechendasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.AnimatedCircleChartScreen
import com.example.lechendasapp.utils.BottomNavBar


@Composable
fun HomeScreen(
    onBack: () -> Unit,
    currentRoute: String = "home",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { CustomBoxLayout(onAddClick = onAddClick) },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onMenuClick,
                onSearch = onSearchClick,
                onSettings = onSettingsClick,
        )
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CustomBoxLayout(
    onAddClick: () -> Unit,
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

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 20.dp, vertical = 10.dp)
//
//        ){
//
//            Image(
//                painter = painterResource(id = R.drawable.capybara),
//                contentDescription = null,
//                modifier = Modifier
//                .size(40.dp)
//            )
//
//            Image(
//                painter = painterResource(id = R.drawable.profilepicture),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//            )
//        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text("Bienvenido",
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
            IconButton(
                onClick = onAddClick
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
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedCircleChartScreen(modifier = modifier)
    }
}


@ScreenPreviews
@Composable
fun HomeScreenPreview() {
    LechendasAppTheme {
        HomeScreen(
            onBack = {},
            currentRoute = "home",
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            onAddClick = {}
        )
    }
}