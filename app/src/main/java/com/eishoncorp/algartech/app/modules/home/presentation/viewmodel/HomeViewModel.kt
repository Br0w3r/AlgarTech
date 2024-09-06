package com.eishoncorp.algartech.app.modules.home.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PredictionPlace
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel
import com.eishoncorp.algartech.core.domain.usecases.GooglePlaces.AutoCompletePlacesUseCase
import com.eishoncorp.algartech.core.domain.usecases.GooglePlaces.GetPlaceLocationUseCase
import com.eishoncorp.algartech.core.domain.usecases.Location.GetLocationUseCase
import com.eishoncorp.algartech.core.domain.usecases.Weather.GetCurrentWeatherUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getPlacesUseCase: AutoCompletePlacesUseCase,
    private val getPlaceLocationUseCase: GetPlaceLocationUseCase,
) : AndroidViewModel(application) {

    // Search
    private val _findText = MutableLiveData<String>()
    val findText: LiveData<String> = _findText

    // GooglePlaces
    private val _places = MutableLiveData<List<PredictionPlace>>()
    val places: LiveData<List<PredictionPlace>> = _places

    // Current location
    private val _currentLocation = MutableLiveData<LatLng?>()
    val currentLocation: LiveData<LatLng?> = _currentLocation

    // Weather
    private val _weather = MutableLiveData<WeatherModel?>()
    val weather: LiveData<WeatherModel?> = _weather

    private var retryCount = 0
    private val maxRetries = 3

    fun onSearchChange(text: String) {
        _findText.value = text
        fetchAutoCompletePlaces(text)
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            try {
                val location = getLocationUseCase()
                if (location != null) {
                    _currentLocation.postValue(location)
                    fetchWeather(location.latitude, location.longitude)
                    retryCount = 0 // Resetear contador de reintentos
                } else {
                    Log.e("HomeViewModel", "Location is null, retrying...")
                    if (retryCount < maxRetries) {
                        retryCount++
                        retryFetchLocationWithDelay()
                    } else {
                        Log.e("HomeViewModel", "Max retries reached. Stopping retries.")
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching current location", e)
            }
        }
    }

    private suspend fun retryFetchLocationWithDelay() {
        kotlinx.coroutines.delay(3000)
        fetchCurrentLocation()
    }

    private fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val weatherData = getCurrentWeatherUseCase(
                    latitude,
                    longitude,
                    getApplication<Application>().getString(R.string.open_weather_key)
                )
                _weather.postValue(weatherData)
            } catch (e: Exception) {
                Log.e("Weather", "Error fetching weather data", e)
            }
        }
    }

    private fun fetchAutoCompletePlaces(query: String) {
        viewModelScope.launch {
            try {
                val places = getPlacesUseCase(
                    query,
                    getApplication<Application>().getString(R.string.google_key)
                )

                _places.postValue(places?.predictions ?: emptyList())

            } catch (e: Exception) {
                Log.e("AutoCompletePlaces", "Error fetching autocomplete places", e)
            }
        }
    }

    fun onPlaceSelected(place: PredictionPlace) {
        onClearText()
        _findText.value = place.description

        //Search coordenates selected place
        fetchPlaceLocation(place.place_id)
    }

    private fun fetchPlaceLocation(placeId: String) {
        viewModelScope.launch {
            try {
                val placesLocation = getPlaceLocationUseCase(
                    placeId,
                    getApplication<Application>().getString(R.string.google_key)
                )

                if (placesLocation != null) {
                    _currentLocation.postValue(
                        LatLng(
                            placesLocation.result.geometry.location.lat,
                            placesLocation.result.geometry.location.lng
                        )
                    )
                    fetchWeather(
                        placesLocation.result.geometry.location.lat,
                        placesLocation.result.geometry.location.lng
                    )
                }

            } catch (e: Exception) {
                Log.e("AutoCompletePlaces", "Error fetching autocomplete places", e)
            }
        }
    }

    fun onClearText() {
        _findText.value = ""
        _places.value = emptyList()
    }
}
