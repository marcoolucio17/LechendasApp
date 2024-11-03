package com.example.lechendasapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lechendasapp.screens.ClimateScreen
import com.example.lechendasapp.screens.ConfigurationScreen
import com.example.lechendasapp.screens.ForgotPasswordScreen
import com.example.lechendasapp.screens.FormularyInitialScreen
import com.example.lechendasapp.screens.HomeScreen
import com.example.lechendasapp.screens.IntroScreen
import com.example.lechendasapp.screens.LoginScreen
import com.example.lechendasapp.screens.NewPasswordScreen
import com.example.lechendasapp.screens.SearchScreen
import com.example.lechendasapp.screens.VerifyUserScreen


@Composable
fun LechendasNavGraph(
    startDestination: String = LechendasDestinations.INTRO_ROUTE,
    navController: NavHostController = rememberNavController(),
    navActions: LechendasNavigationActions = remember(NavController) {
        LechendasNavigationActions(navController)
    },
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = LechendasDestinations.INTRO_ROUTE) {
            IntroScreen(
                onLogin = { navActions.navigateToLogin() },
            )
        }
        composable(route = LechendasDestinations.LOGIN_ROUTE) {
            LoginScreen(
                onBack = { navController.navigateUp() },
                onLoginSuccess = { navActions.navigateToHome() }
            )
        }
        composable(route = LechendasDestinations.NEW_PASSWORD_ROUTE) {
            NewPasswordScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = LechendasDestinations.VERIFY_ROUTE) {
            VerifyUserScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = LechendasDestinations.FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = LechendasDestinations.HOME_ROUTE) {
            HomeScreen(
                onBack = { navController.navigateUp() },
                currentRoute = LechendasDestinations.HOME_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onAddClick = { navActions.navigateToFormulary() }
            )
        }
        composable (route = LechendasDestinations.SEARCH_ROUTE) {
            SearchScreen(
                onBack = { navController.navigateUp() },
                currentRoute = LechendasDestinations.SEARCH_ROUTE,
                onHome = { navActions.navigateToHome() },
                onSearch = { navActions.navigateToSearch() },
                onSettings = { navActions.navigateToConfiguration() }
            )
        }
        composable(route = LechendasDestinations.FORMULARY_ROUTE) {
            FormularyInitialScreen(
                onBack = { navController.navigateUp() },
                currentRoute = LechendasDestinations.FORMULARY_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onClimateClick = { navActions.navigateToClimate() }
            )
        }
        composable(route = LechendasDestinations.CONFIGURATION_ROUTE) {
            ConfigurationScreen(
                onBack = { navController.navigateUp() },
                currentRoute = LechendasDestinations.CONFIGURATION_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() }
            )
        }
        composable(route = LechendasDestinations.CLIMATE_ROUTE) {
            ClimateScreen(
                onBack = { navController.navigateUp() },
                currentRoute = LechendasDestinations.CLIMATE_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() }
            )
        }
    }
}
