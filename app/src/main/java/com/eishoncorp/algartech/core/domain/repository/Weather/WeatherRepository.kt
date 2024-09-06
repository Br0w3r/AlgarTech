package com.eishoncorp.algartech.core.domain.repository.Weather

import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherModel?
}