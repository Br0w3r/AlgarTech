package com.eishoncorp.algartech.core.domain.repository.googlePlaces

import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesLocation
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesModel

interface PlacesRepository {
    suspend fun getAutoCompletePlaces(
        input: String,
        apiKey: String
    ): PlacesModel?

    suspend fun getPlacesLocation(
        placeId: String,
        apiKey: String
    ): PlacesLocation?
}