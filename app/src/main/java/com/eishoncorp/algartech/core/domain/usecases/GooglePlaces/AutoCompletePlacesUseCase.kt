package com.eishoncorp.algartech.core.domain.usecases.GooglePlaces

import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesModel
import com.eishoncorp.algartech.core.domain.repository.googlePlaces.PlacesRepository

class AutoCompletePlacesUseCase(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(
        query: String,
        apiKey: String
    ): PlacesModel? {
        return placesRepository.getAutoCompletePlaces(query, apiKey)
    }
}