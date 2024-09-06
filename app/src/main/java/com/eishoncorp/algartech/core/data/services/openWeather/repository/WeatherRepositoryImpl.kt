package com.eishoncorp.algartech.core.data.services.openWeather.repository

import android.util.Log
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel
import com.eishoncorp.algartech.core.data.services.openWeather.services.OpenWeatherService
import com.eishoncorp.algartech.core.domain.repository.Weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepositoryImpl(
    private val weatherService: OpenWeatherService
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherModel? {
        return withContext(Dispatchers.IO) {
            try {

                // Llamada síncrona utilizando `execute`
                val response: Response<WeatherModel> =
                    weatherService.getCurrentWeather(latitude, longitude, apiKey, "metric")
                        .execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("Error", "Failed to fetch weather, Response Code: ${response.code()}")
                    null
                }

            } catch (e: Exception) {
                Log.e("WeatherRepository", "Error fetching weather data", e)
                null
            }
        }
    }
}