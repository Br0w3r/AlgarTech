package com.eishoncorp.algartech.core.domain.usecases.Location

import com.eishoncorp.algartech.core.domain.repository.Location.LocationRepository

class IsLocationPermissionGrantedUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Boolean {
        return locationRepository.isLocationPermissionGranted()
    }
}