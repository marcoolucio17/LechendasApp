package com.example.lechendasapp.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.R


@Composable
fun TopBar2(
    onBack: () -> Unit,
    @StringRes title: Int? = null,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.height(dimensionResource(R.dimen.top_bar_height))) {
        Image(
            painter = painterResource(id = R.drawable.vector_5svg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
        Image(
            painter = painterResource(R.drawable.vector_6svg),
            contentDescription = "Logo 2",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.3f)
                .height(200.dp)
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp)) {
            TextButton(onClick = onBack) {
                Image(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier
                        .size(40.dp) // Make the image fill the whole Box
                )
            }
            Text(
                text = title?.let { stringResource(it) } ?: "",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 0.dp),
            )
        }
    }
}