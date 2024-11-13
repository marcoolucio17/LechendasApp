package com.example.lechendasapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auth0.android.Auth0
import com.auth0.android.result.Credentials
import com.example.lechendasapp.navigation.LechendasDestinations.CLIMATE_EDIT_ROUTE
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
import com.example.lechendasapp.navigation.LechendasDestinations.COVERAGE_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.MONITOR_LOG_ID_ARG
import com.example.lechendasapp.navigation.LechendasDestinations.EDIT_PROFILE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_CLIMATE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_COVERAGE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_TRANSECT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_TRAP_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_VEGETATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRANSECT_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRAPS_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VEGETATION_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.ID_ARG
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
import com.example.lechendasapp.screens.EditProfileScreen
import com.example.lechendasapp.screens.SearchClimateScreen
import com.example.lechendasapp.screens.SearchCoverageScreen
import com.example.lechendasapp.screens.SearchTransectScreen
import com.example.lechendasapp.screens.SearchTrapScreen
import com.example.lechendasapp.screens.SearchVegetationScreen
import com.example.lechendasapp.screens.VerifyUserScreen
import com.example.lechendasapp.viewmodels.AuthViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun LechendasNavGraph(
    startDestination: String = LechendasDestinations.INTRO_ROUTE,
    navController: NavHostController = rememberNavController(),
    auth0: AuthViewModel = hiltViewModel(),
    navActions: LechendasNavigationActions = remember(NavController) {
        LechendasNavigationActions(navController)
    },
    modifier: Modifier = Modifier
) {
    var credentials by remember { mutableStateOf<Credentials?>(null) }
    var loggedIn by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Logout Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Sign Out") },
            text = { Text("Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        loggedIn = false
                        credentials = null
                        navController.navigate(INTRO_ROUTE) {
                            popUpTo(HOME_ROUTE) { inclusive = true }
                        }
                    }
                ) {
                    Text("Sign Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

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
                onLoginSuccess = {
                    navActions.navigateToHome()
                    credentials = it
                    loggedIn = true
                }
            )
        }
        composable(route = HOME_ROUTE) {
            BackHandler {
                showLogoutDialog = true
            }
            HomeScreen(
                onBack = { showLogoutDialog = true },
                //onBack = { navController.navigateUp() },
                currentRoute = HOME_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onAddClick = { navActions.navigateToFormulary() },
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
        composable (route = SEARCH_ROUTE) {
            SearchScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_ROUTE,
                onHome = { navActions.navigateToHome() },
                onSearch = { navActions.navigateToSearch() },
                onSettings = { navActions.navigateToConfiguration() },
                onTransectClick = {monitorLogId -> navActions.navigateToSearchTransect(monitorLogId) },
                onClimateClick = {monitorLogId -> navActions.navigateToSearchClimate(monitorLogId) },
                onCoverageClick = {monitorLogId -> navActions.navigateToSearchCoverage(monitorLogId) },
                onTrapClick = { monitorLogId -> navActions.navigateToSearchTrap(monitorLogId) },
                onVegetationClick = {monitorLogId -> navActions.navigateToSearchVegetation(monitorLogId) }
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
                onSettingsClick = { navActions.navigateToConfiguration() },
                onEditProfile = { navActions.navigateToEditProfile() },
                onLogoutConfirmed = { navActions.navigateToLogin() }
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
        composable(route = CLIMATE_EDIT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            val id = backStackEntry.arguments?.getString(ID_ARG)
            requireNotNull(id) { "Id is required as an argument" }
            ClimateScreen(
                onBack = { navController.navigateUp() },
                currentRoute = CLIMATE_EDIT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
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
        composable(route = TRAPS_EDIT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            val id = backStackEntry.arguments?.getString(ID_ARG)
            requireNotNull(id) { "Id is required as an argument" }

            TrapFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = TRAPS_EDIT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
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
        composable(route = COVERAGE_EDIT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }

            val id = backStackEntry.arguments?.getString(ID_ARG)
            requireNotNull(id) { "Id is required as an argument" }

            CoverageFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = COVERAGE_EDIT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
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
        composable(route = VEGETATION_EDIT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }

            val id = backStackEntry.arguments?.getString(ID_ARG)
            requireNotNull(id) { "Id is required as an argument" }

            VegetationFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = VEGETATION_EDIT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
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
        composable (route = TRANSECT_EDIT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            val id = backStackEntry.arguments?.getString(ID_ARG)
            requireNotNull(id) { "Id is required as an argument" }
            TransectFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = TRANSECT_EDIT_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
            )
        }

        composable(route = EDIT_PROFILE_ROUTE) {
            EditProfileScreen(
                onBack = { navController.navigateUp() }
            )
        }

        composable(route = SEARCH_TRANSECT_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            SearchTransectScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_TRANSECT_ROUTE,
                onSearch = { navActions.navigateToSearch() },
                onHome = { navActions.navigateToHome() },
                onSettings = { navActions.navigateToConfiguration() },
                onEdit = {id, monitorLogId -> navActions.navigateToTransectEdit(id, monitorLogId) },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = SEARCH_TRAP_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            SearchTrapScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_TRAP_ROUTE,
                onSearch = { navActions.navigateToSearch() },
                onHome = { navActions.navigateToHome() },
                onSettings = { navActions.navigateToConfiguration() },
                onEdit = {id, monitorLogId -> navActions.navigateToTrapsEdit(id, monitorLogId) },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = SEARCH_CLIMATE_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            SearchClimateScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_CLIMATE_ROUTE,
                onSearch = { navActions.navigateToSearch() },
                onHome = { navActions.navigateToHome() },
                onSettings = { navActions.navigateToConfiguration() },
                onEdit = {id, monitorLogId -> navActions.navigateToClimateEdit(id, monitorLogId) },
                monitorLogId = monitorLogId.toLong(),
            )
        }
        composable(route = SEARCH_COVERAGE_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            SearchCoverageScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_COVERAGE_ROUTE,
                onSearch = { navActions.navigateToSearch() },
                onHome = { navActions.navigateToHome() },
                onSettings = { navActions.navigateToConfiguration() },
                monitorLogId = monitorLogId.toLong(),
                onEdit = {id, monitorLogId -> navActions.navigateToCoverageEdit(id, monitorLogId) }
            )
        }
        composable(route = SEARCH_VEGETATION_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            SearchVegetationScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_VEGETATION_ROUTE,
                onSearch = { navActions.navigateToSearch() },
                onHome = { navActions.navigateToHome() },
                onSettings = { navActions.navigateToConfiguration() },
                onEdit = {id, monitorLogId -> navActions.navigateToVegetationEdit(id, monitorLogId) },
                monitorLogId = monitorLogId.toLong()
            )
        }
    }
}
