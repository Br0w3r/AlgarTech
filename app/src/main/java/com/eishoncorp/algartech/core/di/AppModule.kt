// AppModule.kt
package com.eishoncorp.algartech.core.di

import android.app.Application
import android.content.Context
import com.eishoncorp.algartech.core.data.manager.LocalUserMangerImpl
import com.eishoncorp.algartech.core.data.manager.Location.Repository.LocationRepositoryImpl
import com.eishoncorp.algartech.core.data.services.googlePlaces.repository.PlacesRepositoryImpl
import com.eishoncorp.algartech.core.data.services.googlePlaces.services.GooglePlacesService
import com.eishoncorp.algartech.core.data.services.openWeather.repository.WeatherRepositoryImpl
import com.eishoncorp.algartech.core.data.services.openWeather.services.OpenWeatherService
import com.eishoncorp.algartech.shared.utils.Constants
import com.eishoncorp.algartech.core.domain.manager.ActivityProvider
import com.eishoncorp.algartech.core.domain.manager.DefaultActivityProvider
import com.eishoncorp.algartech.core.domain.usecases.AppEntry.AppEntryUseCases
import com.eishoncorp.algartech.core.domain.usecases.AppEntry.ReadAppEntry
import com.eishoncorp.algartech.core.domain.usecases.AppEntry.SaveAppEntry
import com.eishoncorp.algartech.core.domain.manager.LocalUserManager
import com.eishoncorp.algartech.core.domain.repository.Location.LocationRepository
import com.eishoncorp.algartech.core.domain.repository.Weather.WeatherRepository
import com.eishoncorp.algartech.core.domain.repository.googlePlaces.PlacesRepository
import com.eishoncorp.algartech.core.domain.usecases.GooglePlaces.AutoCompletePlacesUseCase
import com.eishoncorp.algartech.core.domain.usecases.GooglePlaces.GetPlaceLocationUseCase
import com.eishoncorp.algartech.core.domain.usecases.Location.GetLocationUseCase
import com.eishoncorp.algartech.core.domain.usecases.Location.IsLocationPermissionGrantedUseCase
import com.eishoncorp.algartech.core.domain.usecases.Location.RequestLocationPermissionUseCase
import com.eishoncorp.algartech.core.domain.usecases.Weather.GetCurrentWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindActivityProvider(
        defaultActivityProvider: DefaultActivityProvider
    ): ActivityProvider

    companion object {

        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(
            @ApplicationContext context: Context
        ): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }

        @Provides
        @Singleton
        fun provideLocationRepository(
            activityProvider: ActivityProvider,
            fusedLocationProviderClient: FusedLocationProviderClient
        ): LocationRepository {
            return LocationRepositoryImpl(activityProvider, fusedLocationProviderClient)
        }

        @Provides
        @Singleton
        fun provideIsLocationPermissionUseCase(locationRepository: LocationRepository): IsLocationPermissionGrantedUseCase {
            return IsLocationPermissionGrantedUseCase(locationRepository)
        }

        @Provides
        @Singleton
        fun provideRequestLocationPermissionUseCase(locationRepository: LocationRepository): RequestLocationPermissionUseCase {
            return RequestLocationPermissionUseCase(locationRepository)
        }

        @Provides
        @Singleton
        fun provideGetCurrentLocationUseCase(locationRepository: LocationRepository): GetLocationUseCase {
            return GetLocationUseCase(locationRepository)
        }

        @Provides
        @Singleton
        fun provideLocalUserManager(
            application: Application
        ): LocalUserManager = LocalUserMangerImpl(context = application)

        @Provides
        @Singleton
        fun provideAppEntryUseCases(
            localUserManager: LocalUserManager
        ): AppEntryUseCases = AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )

        @Provides
        @Singleton
        fun provideOpenWeatherService(@ApplicationContext context: Context): OpenWeatherService {
            return OpenWeatherService(context)
        }

        @Provides
        @Singleton
        fun provideWeatherRepository(openWeatherService: OpenWeatherService): WeatherRepository {
            return WeatherRepositoryImpl(openWeatherService)
        }

        @Provides
        @Singleton
        fun provideWeatherUseCase(weatherRepository: WeatherRepository): GetCurrentWeatherUseCase {
            return GetCurrentWeatherUseCase(weatherRepository)
        }

        @Provides
        @Singleton
        fun provideGooglePlacesService(@ApplicationContext context: Context): GooglePlacesService {
            return GooglePlacesService(context)
        }

        @Provides
        @Singleton
        fun providePlacesRepository(placesService: GooglePlacesService): PlacesRepository {
            return PlacesRepositoryImpl(placesService)
        }

        @Provides
        @Singleton
        fun provideAutoCompletePlacesUseCase(placesRepository: PlacesRepository): AutoCompletePlacesUseCase {
            return AutoCompletePlacesUseCase(placesRepository)
        }

        @Provides
        @Singleton
        fun provideGetPlaceLocationUseCaseUseCase(placesRepository: PlacesRepository): GetPlaceLocationUseCase {
            return GetPlaceLocationUseCase(placesRepository)
        }


    }
}
