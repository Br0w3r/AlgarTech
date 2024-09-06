package com.eishoncorp.algartech.shared.routes

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")

    object HomeScreen : Route(route = "homeScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object AlgarTechNavigation : Route(route = "algarTechNavigation")
}