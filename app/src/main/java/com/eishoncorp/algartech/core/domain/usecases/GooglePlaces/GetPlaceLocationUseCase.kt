package com.eishoncorp.algartech.core.domain.usecases.GooglePlaces

import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesLocation
import com.eishoncorp.algartech.core.domain.repository.googlePlaces.PlacesRepository

class GetPlaceLocationUseCase(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(
        placeId: String,
        apiKey: String
    ): PlacesLocation? {
        return placesRepository.getPlacesLocation(placeId, apiKey)
    }
}