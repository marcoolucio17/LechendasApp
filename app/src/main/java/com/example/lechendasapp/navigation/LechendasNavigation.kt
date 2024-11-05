package com.example.lechendasapp.navigation

import androidx.navigation.NavController
import com.example.lechendasapp.navigation.LechendasDestinations.CONFIGURATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.COVERAGE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORGOT_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORMULARY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.HOME_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.INTRO_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.LOGIN_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.NEW_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRAPS_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VEGETATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VERIFY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.MONITOR_LOG_ID_ARG
import com.example.lechendasapp.navigation.LechendasScreens.CLIMATE_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.CONFIGURATION_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.COVERAGE_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.FORGOT_PASSWORD_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.FORMULARY_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.HOME_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.INTRO_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.LOGIN_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.NEW_PASSWORD_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.SEARCH_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.TRANSECT_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.TRAPS_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.VEGETATION_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.VERIFY_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.COUNTING_SCREEN
import com.example.lechendasapp.navigation.LechendasScreens.EDIT_PROFILE_SCREEN


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
    const val CONFIGURATION_SCREEN = "configuration"
    const val CLIMATE_SCREEN = "climate"
    const val TRAPS_SCREEN = "traps"
    const val COVERAGE_SCREEN = "coverage"
    const val VEGETATION_SCREEN = "vegetation"
    const val TRANSECT_SCREEN = "transect"
    const val COUNTING_SCREEN = "counting"
    const val EDIT_PROFILE_SCREEN = "editProfile"
}

/*For the future: arguments for the routes */
object LechendasDestinationsArgs {
    const val MONITOR_LOG_ID_ARG = "monitorLogId"
}

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
    const val CONFIGURATION_ROUTE = CONFIGURATION_SCREEN
    const val CLIMATE_ROUTE = "$CLIMATE_SCREEN/{$MONITOR_LOG_ID_ARG}"
    const val TRAPS_ROUTE = "$TRAPS_SCREEN/{$MONITOR_LOG_ID_ARG}"
    const val COVERAGE_ROUTE = "$COVERAGE_SCREEN/{$MONITOR_LOG_ID_ARG}"
    const val VEGETATION_ROUTE = "$VEGETATION_SCREEN/{$MONITOR_LOG_ID_ARG}"
    const val TRANSECT_ROUTE = "$TRANSECT_SCREEN/{$MONITOR_LOG_ID_ARG}"
    const val COUNTING_ROUTE = COUNTING_SCREEN
    const val EDIT_PROFILE_ROUTE = EDIT_PROFILE_SCREEN
}

class LechendasNavigationActions(private val navController: NavController) {
    fun navigateToIntro() {
        navController.navigate(INTRO_ROUTE)
    }

    fun navigateToLogin() {
        navController.navigate(LOGIN_ROUTE)
    }

    fun navigateToNewPassword() {
        navController.navigate(NEW_PASSWORD_ROUTE)
    }

    fun navigateToVerify() {
        navController.navigate(VERIFY_ROUTE)
    }

    fun navigateToForgotPassword() {
        navController.navigate(FORGOT_PASSWORD_ROUTE)
    }

    fun navigateToHome() {
        navController.navigate(HOME_ROUTE)
    }

    fun navigateToSearch() {
        navController.navigate(SEARCH_ROUTE)
    }

    fun navigateToFormulary() {
        navController.navigate(FORMULARY_ROUTE)
    }

    fun navigateToConfiguration() {
        navController.navigate(CONFIGURATION_ROUTE)
    }

    fun navigateToClimate(monitorLogId: Long) {
        navController.navigate("$CLIMATE_SCREEN/$monitorLogId")
    }

    fun navigateToTraps(monitorLogId: Long) {
        navController.navigate("$TRAPS_SCREEN/$monitorLogId")
    }
    fun navigateToCoverage(monitorLogId: Long) {
        navController.navigate("$COVERAGE_SCREEN/$monitorLogId")
    }

    fun navigateToVegetation(monitorLogId: Long) {
        navController.navigate("$VEGETATION_SCREEN/$monitorLogId")
    }

    fun navigateToTransect(monitorLogId: Long) {
        navController.navigate("$TRANSECT_SCREEN/$monitorLogId")
    }
    fun navigateToCounting() {
        navController.navigate(LechendasDestinations.COUNTING_ROUTE)
    }
    fun navigateToEditProfile() {
        navController.navigate(LechendasDestinations.EDIT_PROFILE_ROUTE)
    }
}


