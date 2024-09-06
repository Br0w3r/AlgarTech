package com.eishoncorp.algartech.core.data.services.googlePlaces.models

data class PlacesLocation(
    val result: Result,
    val status: String
)

data class Result(
    val geometry: Geometry
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)
