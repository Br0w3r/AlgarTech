package com.eishoncorp.algartech.core.data.services.googlePlaces.repository

import android.util.Log
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesLocation
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesModel
import com.eishoncorp.algartech.core.data.services.googlePlaces.services.GooglePlacesService
import com.eishoncorp.algartech.core.domain.repository.googlePlaces.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

import javax.inject.Inject

class PlacesRepositoryImpl(
    private val googlPlacesService: GooglePlacesService
) : PlacesRepository {

    override suspend fun getAutoCompletePlaces(
        input: String,
        apiKey: String
    ): PlacesModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = googlPlacesService.getPlaces(
                    input,
                    apiKey,
                    "country:mx"
                )
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("Error", "Failed to fetch places, Response Code: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PlacesRepository", "Error fetching places data", e)
                null
            }
        }
    }

    override suspend fun getPlacesLocation(
        placeId: String,
        apiKey: String
    ): PlacesLocation? {
        return withContext(Dispatchers.IO) {
            try {
                val response = googlPlacesService.getPlacesLocation(
                    placeId,
                    "geometry/location",
                    apiKey,
                )
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("Error", "Failed to fetch places, Response Code: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PlacesRepository", "Error fetching places data", e)
                null
            }
        }
    }
}