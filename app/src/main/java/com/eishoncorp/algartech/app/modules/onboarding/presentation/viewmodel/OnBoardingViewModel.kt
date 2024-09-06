package com.eishoncorp.algartech.app.modules.onboarding.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eishoncorp.algartech.app.modules.onboarding.presentation.event.OnBoardingEvent
import com.eishoncorp.algartech.app.modules.onboarding.presentation.event.OnBoardingState
import com.eishoncorp.algartech.core.domain.usecases.AppEntry.AppEntryUseCases
import com.eishoncorp.algartech.core.domain.usecases.Location.IsLocationPermissionGrantedUseCase
import com.eishoncorp.algartech.core.domain.usecases.Location.RequestLocationPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val requestLocationPermissionUseCase: RequestLocationPermissionUseCase,
    private val isLocationPermissionUseCase: IsLocationPermissionGrantedUseCase,
) : ViewModel() {

    private val _onBoardingState =
        MutableStateFlow(OnBoardingState(isLocationPermissionGranted = false))
    val onBoardingState: StateFlow<OnBoardingState> = _onBoardingState.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.SaveAppEntry -> {
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

    suspend fun requestLocationPermission(context: Context) {
        // Verifica si el permiso ya está concedido
        if (isLocationPermissionUseCase()) {
            // Actualiza el estado si el permiso ya está concedido
            updateLocationPermissionGranted(true)
        } else {
            // Si no, solicita el permiso
            requestLocationPermissionUseCase()
        }
    }

    fun updateLocationPermissionGranted(isGranted: Boolean) {
        _onBoardingState.value =
            _onBoardingState.value.copy(isLocationPermissionGranted = isGranted)
    }
}

