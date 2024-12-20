package com.example.lechendasapp.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lechendasapp.MainActivity
import com.example.lechendasapp.navigation.LechendasDestinations.CAMERA_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.CLIMATE_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.CLIMATE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.CONFIGURATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.COVERAGE_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.COVERAGE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.EDIT_PROFILE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORGOT_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.FORMULARY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.HOME_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.INTRO_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.LOGIN_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.NEW_PASSWORD_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_CLIMATE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_COVERAGE_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_TRANSECT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_TRAP_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.SEARCH_VEGETATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRANSECT_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRANSECT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRAPS_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.TRAPS_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VEGETATION_EDIT_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VEGETATION_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinations.VERIFY_ROUTE
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.ID_ARG
import com.example.lechendasapp.navigation.LechendasDestinationsArgs.MONITOR_LOG_ID_ARG
import com.example.lechendasapp.screens.CameraPreview
import com.example.lechendasapp.screens.ClimateScreen
import com.example.lechendasapp.screens.ConfigurationScreen
import com.example.lechendasapp.screens.CoverageFormsScreen
import com.example.lechendasapp.screens.EditProfileScreen
import com.example.lechendasapp.screens.ForgotPasswordScreen
import com.example.lechendasapp.screens.FormularyInitialScreen
import com.example.lechendasapp.screens.HomeScreen
import com.example.lechendasapp.screens.IntroScreen
import com.example.lechendasapp.screens.LoginScreen
import com.example.lechendasapp.screens.NewPasswordScreen
import com.example.lechendasapp.screens.SearchClimateScreen
import com.example.lechendasapp.screens.SearchCoverageScreen
import com.example.lechendasapp.screens.SearchScreen
import com.example.lechendasapp.screens.SearchTransectScreen
import com.example.lechendasapp.screens.SearchTrapScreen
import com.example.lechendasapp.screens.SearchVegetationScreen
import com.example.lechendasapp.screens.TransectFormsScreen
import com.example.lechendasapp.screens.TrapFormsScreen
import com.example.lechendasapp.screens.VegetationFormsScreen
import com.example.lechendasapp.screens.VerifyUserScreen
import com.example.lechendasapp.viewmodels.NavGraphViewModel



@SuppressLint("NewApi")
/*TODO: fix this*/
@Composable
fun LechendasNavGraph(
    startDestination: String = INTRO_ROUTE,
    navController: NavHostController = rememberNavController(),
    navActions: LechendasNavigationActions = remember(NavController) {
        LechendasNavigationActions(navController)
    },
    viewModel: NavGraphViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val showLogoutDialog by viewModel.showLogoutDialog.collectAsState()
    val isLoggedIn by viewModel.loggedIn.collectAsState()

    // Logout Dialog
    if (showLogoutDialog) {
        ShowLogoutDialog(navController)
    }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val protectedRoutes = listOf(
                HOME_ROUTE,
                CONFIGURATION_ROUTE,
                FORMULARY_ROUTE,
                SEARCH_ROUTE
            )

            if (protectedRoutes.contains(destination.route) && !isLoggedIn) {
                navController.navigate(INTRO_ROUTE) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = INTRO_ROUTE) {
            IntroScreen(
                onLogin = { navActions.navigateToLogin() },
                navigateToHome = { navActions.navigateToHome() },
                viewModel = viewModel
            )
        }
        composable(route = LOGIN_ROUTE) {
            LoginScreen(
                onBack = { navController.navigateUp() },
                onLoginSuccess = {
                 viewModel.setCredentials(it)
                 navActions.navigateToHome()
                }
            )
        }
        composable(route = HOME_ROUTE) {
            BackHandler {
                viewModel.setShowLogoutDialog(true)
            }
            HomeScreen(
                onBack = { viewModel.setShowLogoutDialog(true) },
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
        composable(route = CAMERA_ROUTE) {
            CameraPreview(
                activity = navController.context as MainActivity,
                onBack = { navController.navigateUp() }
            )
        }
        composable(route = SEARCH_ROUTE) {
            SearchScreen(
                onBack = { navController.navigateUp() },
                currentRoute = SEARCH_ROUTE,
                onHome = { navActions.navigateToHome() },
                onSearch = { navActions.navigateToSearch() },
                onSettings = { navActions.navigateToConfiguration() },
                onTransectClick = { monitorLogId -> navActions.navigateToSearchTransect(monitorLogId) },
                onClimateClick = { monitorLogId -> navActions.navigateToSearchClimate(monitorLogId) },
                onCoverageClick = { monitorLogId -> navActions.navigateToSearchCoverage(monitorLogId) },
                onTrapClick = { monitorLogId -> navActions.navigateToSearchTrap(monitorLogId) },
                onVegetationClick = { monitorLogId ->
                    navActions.navigateToSearchVegetation(
                        monitorLogId
                    )
                }
            )
        }
        composable(route = FORMULARY_ROUTE) {
            FormularyInitialScreen(
                onBack = { navController.navigateUp() },
                currentRoute = FORMULARY_ROUTE,
                onMenuClick = { navActions.navigateToHome() },
                onSearchClick = { navActions.navigateToSearch() },
                onSettingsClick = { navActions.navigateToConfiguration() },
                onTransectClick = { monitorLogId -> navActions.navigateToTransect(monitorLogId) },
                onClimateClick = { monitorLogId -> navActions.navigateToClimate(monitorLogId) },
                onCoverageClick = { monitorLogId -> navActions.navigateToCoverage(monitorLogId) },
                onTrapClick = { monitorLogId -> navActions.navigateToTraps(monitorLogId) },
                onVegetationClick = { monitorLogId -> navActions.navigateToVegetation(monitorLogId) }
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
                onLogoutConfirmed = { viewModel.setShowLogoutDialog(true) }
            )
        }
        composable(route = CLIMATE_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            ClimateScreen(
                onBack = { navController.navigateUp() },
                currentRoute = CLIMATE_ROUTE,
                onMenuClick = { navActions.navigateFromFormsToHome() },
                onSearchClick = { navActions.navigateFromFormsToSearch() },
                onSettingsClick = { navActions.navigateFromFormsToConfiguration() },
                onCameraClick = { navActions.navigateToCamera() },
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
                onCameraClick = { navActions.navigateToCamera() },
                monitorLogId = monitorLogId.toLong(),
                id = id.toLong()
            )
        }

        composable(route = TRAPS_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            TrapFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = TRAPS_ROUTE,
                onMenuClick = { navActions.navigateFromFormsToHome() },
                onSearchClick = { navActions.navigateFromFormsToSearch() },
                onSettingsClick = { navActions.navigateFromFormsToConfiguration() },
                onCameraClick = { navActions.navigateToCamera() },
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
                onCameraClick = { navActions.navigateToCamera() },
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
                onMenuClick = { navActions.navigateFromFormsToHome() },
                onSearchClick = { navActions.navigateFromFormsToSearch() },
                onSettingsClick = { navActions.navigateFromFormsToConfiguration() },
                onCameraClick = { navActions.navigateToCamera() },
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
                onCameraClick = { navActions.navigateToCamera() },
                id = id.toLong()
            )
        }

        composable(route = VEGETATION_ROUTE) { backStackEntry ->
            val monitorLogId = backStackEntry.arguments?.getString(MONITOR_LOG_ID_ARG)
            requireNotNull(monitorLogId) { "MonitorLogId is required as an argument" }
            VegetationFormsScreen(
                onBack = { navController.navigateUp() },
                currentRoute = VEGETATION_ROUTE,
                onMenuClick = { navActions.navigateFromFormsToHome() },
                onSearchClick = { navActions.navigateFromFormsToSearch() },
                onSettingsClick = { navActions.navigateFromFormsToConfiguration() },
                onCameraClick = { navActions.navigateToCamera() },
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
                onCameraClick = { navActions.navigateToCamera() },
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
                onMenuClick = { navActions.navigateFromFormsToHome() },
                onSearchClick = { navActions.navigateFromFormsToSearch() },
                onSettingsClick = { navActions.navigateFromFormsToConfiguration() },
                onCameraClick = { navActions.navigateToCamera() },
                monitorLogId = monitorLogId.toLong()
            )
        }
        composable(route = TRANSECT_EDIT_ROUTE) { backStackEntry ->
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
                onCameraClick = { navActions.navigateToCamera() },
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
                onEdit = { id, monitorLogId ->
                    navActions.navigateToTransectEdit(
                        id,
                        monitorLogId
                    )
                },
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
                onEdit = { id, monitorLogId -> navActions.navigateToTrapsEdit(id, monitorLogId) },
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
                onEdit = { id, monitorLogId -> navActions.navigateToClimateEdit(id, monitorLogId) },
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
                onEdit = { id, monitorLogId -> navActions.navigateToCoverageEdit(id, monitorLogId) }
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
                onEdit = { id, monitorLogId ->
                    navActions.navigateToVegetationEdit(
                        id,
                        monitorLogId
                    )
                },
                monitorLogId = monitorLogId.toLong()
            )
        }
    }
}

@Composable
fun ShowLogoutDialog(navController: NavController, viewModel: NavGraphViewModel = hiltViewModel()) {
    AlertDialog(
        onDismissRequest = { viewModel.setShowLogoutDialog(false) },
        title = { Text("Cerrar sesión") },
        text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.logout()
                    navController.navigate(INTRO_ROUTE) {
                        popUpTo(0) { inclusive = true }
                    }
                    // Optionally, clear any stored authentication data
                    // auth0.clearAuthState()
                }
            ) {
                Text("Cerrar sesión")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.setShowLogoutDialog(false) }) {
                Text("Cancelar")
            }
        }
    )
}


