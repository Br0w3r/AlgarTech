package com.eishoncorp.algartech.app.modules.onboarding.presentation.event

sealed class OnBoardingEvent {
    object SaveAppEntry : OnBoardingEvent()
}

data class OnBoardingState(
    val isLocationPermissionGranted: Boolean
)
