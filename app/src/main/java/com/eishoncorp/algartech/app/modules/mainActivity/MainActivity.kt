package com.eishoncorp.algartech.app.modules.mainActivity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.DefaultLifecycleObserver
import com.eishoncorp.AlgarTech.ui.theme.AlgarTechTheme
import com.eishoncorp.algartech.app.modules.home.presentation.viewmodel.HomeViewModel
import com.eishoncorp.algartech.app.modules.mainActivity.presentation.viewmodel.MainViewModel
import com.eishoncorp.algartech.core.domain.manager.DefaultActivityProvider
import com.eishoncorp.algartech.core.domain.usecases.AppEntry.AppEntryUseCases
import com.eishoncorp.algartech.shared.navgraph.NavGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var activityProvider: DefaultActivityProvider

    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases

    private val mainViewModel by viewModels<MainViewModel>()
    private val homeViewModel: HomeViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                homeViewModel.fetchCurrentLocation()
            } else {
                Log.e("MainActivity", "Location permission denied")
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Registrar el activityProvider como un observador del ciclo de vida
        lifecycle.addObserver(activityProvider as DefaultLifecycleObserver)

        installSplashScreen().apply {
            setKeepOnScreenCondition { mainViewModel.splashCondition.value }
        }

        setContent {
            AlgarTechTheme(dynamicColor = false) {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemUiColor = rememberSystemUiController()
                SideEffect {
                    systemUiColor.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    NavGraph(
                        startDestination = mainViewModel.startDestination.value,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            homeViewModel.fetchCurrentLocation()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
