package com.example.lechendasapp.navigation

import androidx.navigation.NavController
import com.example.lechendasapp.navigation.LechendasScreens.FORGOT_PASSWORD_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.FORMULARY_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.HOME_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.INTRO_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.LOGIN_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.NEW_PASSWORD_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.SEARCH_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.VERIFY_SCREEN

/* screen used in the app */
private object LechendasScreens {
    const val INTRO_SCREEN = "intro"
    const val LOGIN_SCREEN = "login"
    const val NEW_PASSWORD_SCREEN = "newPassword"
    const val VERIFY_SCREEN = "verify"
    const val FORGOT_PASSWORD_SCREEN = "forgotPassword"
    const val HOME_SCREEN = "home"
    const val SEARCH_SCREEN = "search"
    const val FORMULARY_SCREEN = "formulary"
}

/*For the future: arguments for the routes */
@Suppress("UNUSED")
object LechendasDestinationsArgs

/*Routes for the app (we can add arguments here if we need)
* as shown here:
* https://github.com/android/architecture-samples/blob/main/app/src/main/java/com/example/android/architecture/blueprints/todoapp/TodoNavigation.kt
* */
object LechendasDestinations {
    const val INTRO_ROUTE = INTRO_SCREEN
    const val LOGIN_ROUTE = LOGIN_SCREEN
    const val NEW_PASSWORD_ROUTE = NEW_PASSWORD_SCREEN
    const val VERIFY_ROUTE = VERIFY_SCREEN
    const val FORGOT_PASSWORD_ROUTE = FORGOT_PASSWORD_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
    const val SEARCH_ROUTE = SEARCH_SCREEN
    const val FORMULARY_ROUTE = FORMULARY_SCREEN
}

class LechendasNavigationActions(private val navController: NavController) {
    fun navigateToIntro() {
        navController.navigate(LechendasDestinations.INTRO_ROUTE)
    }

    fun navigateToLogin() {
        navController.navigate(LechendasDestinations.LOGIN_ROUTE)
    }

    fun navigateToNewPassword() {
        navController.navigate(LechendasDestinations.NEW_PASSWORD_ROUTE)
    }

    fun navigateToVerify() {
        navController.navigate(LechendasDestinations.VERIFY_ROUTE)
    }

    fun navigateToForgotPassword() {
        navController.navigate(LechendasDestinations.FORGOT_PASSWORD_ROUTE)
    }

    fun navigateToHome() {
        navController.navigate(LechendasDestinations.HOME_ROUTE)
    }

    fun navigateToSearch() {
        navController.navigate(LechendasDestinations.SEARCH_ROUTE)
    }

    fun navigateToFormulary() {
        navController.navigate(LechendasDestinations.FORMULARY_ROUTE)
    }
}


