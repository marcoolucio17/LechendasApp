package com.example.lechendasapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.lechendasapp.navigation.LechendasNavGraph
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Handle permission results
            val allGranted = permissions.all { it.value }
            if (allGranted) {
                Log.d(TAG, "Todos los permisos concedidos")
            } else {
                Log.e(TAG, "Permisos denegados. La aplicación puede no funcionar correctamente.")
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permisos al inicio si no están otorgados
        if (!arePermissionsGranted()) {
            requestPermissionsLauncher.launch(REQUIRED_PERMISSIONS)
        }

        enableEdgeToEdge()
        setContent {
            LechendasAppTheme {
                LechendasNavGraph()
            }
        }
    }

    // Verificar si los permisos están otorgados
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun arePermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}
