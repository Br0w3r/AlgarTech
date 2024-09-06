package com.eishoncorp.algartech.core.data.services.openWeather.services

import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherContract {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherModel>
}