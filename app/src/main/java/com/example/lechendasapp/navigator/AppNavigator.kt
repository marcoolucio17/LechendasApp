package com.example.lechendasapp.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lechendasapp.views.IntroView

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "intro_view")
    {
        composable("intro_view") {
            IntroView(navController = navController)
        }
    }
}
