package com.eishoncorp.algartech.core.domain.usecases.Weather

import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel
import com.eishoncorp.algartech.core.domain.repository.Weather.WeatherRepository

class GetCurrentWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherModel? {
        return weatherRepository.getCurrentWeather(latitude, longitude, apiKey)
    }

}