package com.eishoncorp.algartech.shared.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.eishoncorp.algartech.app.modules.home.presentation.ui.HomeScreen
import com.eishoncorp.algartech.app.modules.home.presentation.viewmodel.HomeViewModel
import com.eishoncorp.algartech.app.modules.onboarding.presentation.ui.OnBoardingScreen
import com.eishoncorp.algartech.app.modules.onboarding.presentation.viewmodel.OnBoardingViewModel
import com.eishoncorp.algartech.shared.routes.Route

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(
    startDestination: String,
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                )
            }
        }

        navigation(
            route = Route.AlgarTechNavigation.route,
            startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route) {
                HomeScreen(
                    navigate = {},
                    homeViewModel = homeViewModel
                )
            }
        }

    }
}
