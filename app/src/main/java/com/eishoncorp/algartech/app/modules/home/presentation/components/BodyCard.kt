package com.eishoncorp.algartech.app.modules.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel

@Composable
fun BodyCard(
    modifier: Modifier = Modifier,
    weather: WeatherModel?,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        weather?.let {
            Text(
                modifier = Modifier.padding(top = 3.dp),
                text = "Máxima ${it.main.temp_max}°",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 11.dp.value.sp,
            )
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "Mínima ${it.main.temp_min}°",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 11.dp.value.sp,
            )
            HorizontalDivider(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.input_background))
                    .height(2.dp)
            )
        }
    }
}