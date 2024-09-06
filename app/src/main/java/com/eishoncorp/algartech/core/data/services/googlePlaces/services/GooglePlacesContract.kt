package com.eishoncorp.algartech.core.data.services.googlePlaces.services

import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesLocation
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesContract {
    @GET("place/autocomplete/json")
    suspend fun getPlaces(
        @Query("input") input: String,
        @Query("key") apiKey: String,
        @Query("components") components: String = "country:mx",
    ): Response<PlacesModel>

    @GET("place/details/json")
    suspend fun getPlacesLocation(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = "geometry/location",
        @Query("key") apiKey: String,
    ): Response<PlacesLocation>
}