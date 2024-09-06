package com.eishoncorp.algartech.core.domain.usecases.Location

import com.eishoncorp.algartech.core.domain.repository.Location.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LatLng? {
        return locationRepository.getLocation()
    }
}