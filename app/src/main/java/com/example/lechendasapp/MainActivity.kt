package com.example.lechendasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import com.example.lechendasapp.navigator.AppNavigator
import com.example.lechendasapp.views.IntroImage
import com.example.lechendasapp.ui.theme.LechendasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LechendasAppTheme {
                AppNavigator()
            }
        }
    }
}
