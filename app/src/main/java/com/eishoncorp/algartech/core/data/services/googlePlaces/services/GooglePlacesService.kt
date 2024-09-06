package com.eishoncorp.algartech.core.data.services.googlePlaces.services

import android.content.Context
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.network.RetrofitHelper
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesLocation
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PlacesModel
import retrofit2.Call
import retrofit2.Response

class GooglePlacesService(context: Context) : GooglePlacesContract {

    private val baseUrl = context.getString(R.string.google_places_url)

    private val getRetrofit = RetrofitHelper.getRetrofit(baseUrl)

    private val service = getRetrofit.create(GooglePlacesContract::class.java)

    override suspend fun getPlaces(
        input: String,
        apiKey: String,
        components: String
    ): Response<PlacesModel> {
        return service.getPlaces(input, apiKey, components)
    }

    override suspend fun getPlacesLocation(
        placeId: String,
        fields: String,
        apiKey: String
    ): Response<PlacesLocation> {
        return service.getPlacesLocation(placeId, fields, apiKey)
    }

}