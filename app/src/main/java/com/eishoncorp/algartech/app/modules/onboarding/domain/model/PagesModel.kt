package com.eishoncorp.algartech.app.modules.onboarding.domain.model

import androidx.annotation.DrawableRes
import com.eishoncorp.AlgarTech.R

data class PagesModel(
    val title:String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    PagesModel(
        title = "¿Hoy sera un día soleado?",
        description = "Quieres ver el pronóstico del tiempo para hoy, de forma rápida y precísa.",
        image = R.drawable.sun_day
    ),
    PagesModel(
        title = "¿No sabes como estará el clima hoy?",
        description = "Visualiza el clima de tu ubicación actual o de alguna otra dirección que escribas para poder visualizarlo en el mapa.",
        image = R.drawable.rain
    ),
    PagesModel(
        title = "Permisos de ubicación",
        description = "Acepta los permisos de ubicación para que puedas ver tu ubicación en el mapa.",
        image = R.drawable.location
    )
)
