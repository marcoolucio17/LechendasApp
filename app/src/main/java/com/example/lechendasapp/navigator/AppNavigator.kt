package com.example.lechendasapp.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lechendasapp.views.IntroView
import com.example.lechendasapp.views.LoginView
import com.example.lechendasapp.views.NewPasswordScreenView
import com.example.lechendasapp.views.NewPasswordView
import com.example.lechendasapp.views.VerifyUserView

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "intro_view")
    {
        composable("intro_view") {
            IntroView(navController = navController)
        }
        composable("login_view") {
            LoginView(navController = navController)
        }
        composable("new_password") {
            NewPasswordView(navController = navController)
        }
        composable("verify_view") {
            VerifyUserView(navController = navController)
        }
        composable("forgot_password") {
            NewPasswordScreenView(navController = navController)
        }
    }
}
