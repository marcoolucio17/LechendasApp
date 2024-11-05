package com.example.lechendasapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lechendasapp.navigation.LechendasDestinations.CLIMATE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.CONFIGURATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.COVERAGE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORGOT_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORMULARY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.HOME_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.INTRO_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.LOGIN_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.NEW_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRANSECT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRAPS_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VEGETATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VERIFY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.COUNTING_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.MONITOR_LOG_ID_ARG
import com.example.lechendasapp.screens.ClimateScreen
import com.example.lechendasapp.screens.ConfigurationScreen
import com.example.lechendasapp.screens.CoverageFormsScreen
import com.example.lechendasapp.screens.ForgotPasswordScreen
import com.example.lechendasapp.screens.FormularyInitialScreen
import com.example.lechendasapp.screens.HomeScreen
import com.example.lechendasapp.screens.IntroScreen
import com.example.lechendasapp.screens.LoginScreen
import com.example.lechendasapp.screens.NewPasswordScreen
import com.example.lechendasapp.screens.SearchScreen
import com.example.lechendasapp.screens.TransectFormsScreen
import com.example.lechendasapp.screens.TrapFormsScreen
import com.example.lechendasapp.screens.VegetationFormsScreen
import com.example.lechendasapp.screens.CountingFormsScreen
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
        composable(route = INTRO_ROUTE) {
            IntroScreen(
                onLogin = { navActions.navigateToLogin() },
            )
        }
        composable(route = LOGIN_ROUTE) {
            LoginScreen(
                onBack = { navController.navigateUp() },
                onLoginSuccess = { navActions.navigateToHome() }
            )
        }
        composable(route = NEW_PASSWORD_ROUTE) {
            NewPasswordScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = VERIFY_ROUTE) {
            VerifyUserScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(route = HOME_ROUTE) {
            HomeScreen(
                onBack = { navController.navigateUp() },
                currentRoute = HOME_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onAddClick = { navActions.navigateToFormulary() }
            )
        }
        composable (route = SEARCH_ROUTE) {
            SearchScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_ROUTE,
                onHome = { navActions.navigateToHome() },
                onSearch = { navActions.navigateToSearch() },
                onSettings = { navActions.navigateToConfiguration() },
            )
        }
        composable(route = FORMULARY_ROUTE) {
            FormularyInitialScreen(
                onBack = { navController.navigateUp() },
                currentRoute = FORMULARY_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onTransectClick = {monitorLogId -> navActions.navigateToTransect(monitorLogId) },
                onClimateClick = {monitorLogId -> navActions.navigateToClimate(monitorLogId) },
                onCoverageClick = {monitorLogId -> navActions.navigateToCoverage(monitorLogId) },
                onTrapClick = { monitorLogId -> navActions.navigateToTraps(monitorLogId) },
                onVegetationClick = {monitorLogId -> navActions.navigateToVegetation(monitorLogId) }
            )
        }
        composable(route = CONFIGURATION_ROUTE) {
            ConfigurationScreen(
                onBack = { navController.navigateUp() },
                currentRoute = CONFIGURATION_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() }
            )
        }
        composable(route = CLIMATE_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            ClimateScreen(
                onBack = { navController.navigateUp() },
                currentRoute = CLIMATE_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = TRAPS_ROUTE) {backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            TrapFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = TRAPS_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = COVERAGE_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            CoverageFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = COVERAGE_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = VEGETATION_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            VegetationFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = VEGETATION_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = TRANSECT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            TransectFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = TRANSECT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong()
            )
        }
    }
}
