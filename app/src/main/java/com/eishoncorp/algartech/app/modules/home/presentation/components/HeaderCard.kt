package com.eishoncorp.algartech.app.modules.home.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.services.openWeather.models.WeatherModel


@Composable
fun HeaderCard(
    modifier: Modifier = Modifier,
    weather: WeatherModel?,
    context: Context,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        weather?.let {

            Text(
                text = it.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 17.dp.value.sp,
            )
            AsyncImage(
                model = "${context.getString(R.string.open_weather_icon)}${it.weather[0].icon}@2x.png",
                contentDescription = it.weather[0].description,
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp),
                text = "${it.main.temp}°",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.dp.value.sp,
            )
            HorizontalDivider(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.input_background))
                    .height(2.dp)
            )
        }
    }
}