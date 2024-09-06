package com.eishoncorp.algartech.core.data.services.openWeather.services

import android.content.Context
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.network.RetrofitHelper
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel
import retrofit2.Call

class OpenWeatherService(context: Context) : OpenWeatherContract {

    private val baseUrl = context.getString(R.string.open_weather_url)

    private val getRetrofit = RetrofitHelper.getRetrofit(baseUrl)

    private val service = getRetrofit.create(OpenWeatherContract::class.java)

    override fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String,
        units: String
    ): Call<WeatherModel> {
        return service.getCurrentWeather(latitude, longitude, apiKey, units)
    }

}