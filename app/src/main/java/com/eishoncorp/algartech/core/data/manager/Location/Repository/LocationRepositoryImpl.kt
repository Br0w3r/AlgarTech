package com.eishoncorp.algartech.core.data.manager.Location.Repository

import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.activity.ComponentActivity
import com.eishoncorp.algartech.core.domain.manager.ActivityProvider
import com.eishoncorp.algartech.core.domain.repository.Location.LocationRepository
import com.eishoncorp.algartech.shared.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationRepositoryImpl(
    private val activityProvider: ActivityProvider,
    private val fusedLocationClient: FusedLocationProviderClient,
) : LocationRepository {

    override fun isLocationPermissionGranted(): Boolean {
        val activity = activityProvider.getActivity()
        return activity?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } ?: false
    }

    override fun requestLocationPermission() {
        val activity = activityProvider.getActivity()
        if (activity is ComponentActivity) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.PERMISSION_REQUEST_CODE
            )
        } else {
            Log.e("LocationRepository", "Activity is not a ComponentActivity or is null")
        }
    }

    override suspend fun getLocation(): LatLng? {
        return suspendCancellableCoroutine { continuation ->
            if (!isLocationPermissionGranted()) {
                Log.e("LocationRepository", "Permission not granted, requesting permission")
                requestLocationPermission()
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            val cancellationTokenSource = CancellationTokenSource()

            try {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token
                ).addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        continuation.resume(LatLng(location.latitude, location.longitude))
                    } else {
                        Log.e("LocationRepository", "Location is null")
                        continuation.resume(null)
                    }
                }.addOnFailureListener { e ->
                    Log.e("LocationRepository", "Error getting location", e)
                    continuation.resume(null)
                }
            } catch (e: SecurityException) {
                Log.e("LocationRepository", "SecurityException: Permission not granted", e)
                continuation.resume(null)
            }
        }
    }
}
