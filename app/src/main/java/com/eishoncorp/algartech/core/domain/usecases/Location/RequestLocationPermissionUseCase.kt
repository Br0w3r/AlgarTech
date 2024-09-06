package com.eishoncorp.algartech.core.domain.usecases.Location

import com.eishoncorp.algartech.core.domain.repository.Location.LocationRepository

class RequestLocationPermissionUseCase(
    private val locationRepository: LocationRepository
) {
    operator fun invoke() {
        locationRepository.requestLocationPermission()
    }
}
