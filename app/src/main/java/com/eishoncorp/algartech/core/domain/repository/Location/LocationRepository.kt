package com.eishoncorp.algartech.core.domain.repository.Location

import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    fun isLocationPermissionGranted(): Boolean
    fun requestLocationPermission()
    suspend fun getLocation(): LatLng?
}