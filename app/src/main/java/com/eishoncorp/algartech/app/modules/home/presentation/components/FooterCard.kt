package com.eishoncorp.algartech.app.modules.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel


@Composable
fun FooterCard(
    modifier: Modifier = Modifier,
    weather: WeatherModel?,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weather?.let {
            RowTex(title = "Humedad", data = "${weather.main.humidity} %")
            RowTex(title = "Nubes", data = "${weather.clouds.all} %")
            RowTex(title = "Viento", data = "${weather.wind.speed} Km/h")
        }
    }
}


@Composable
fun RowTex(
    title: String,
    data: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, fontSize = 11.dp.value.sp)
        Text(text = data, fontSize = 11.dp.value.sp)
    }
}