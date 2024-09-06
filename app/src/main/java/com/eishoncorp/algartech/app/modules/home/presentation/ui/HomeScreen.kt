package com.eishoncorp.algartech.app.modules.home.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.app.components.cardExpanded.ExpandableCard
import com.eishoncorp.algartech.app.components.search.CustomSearch
import com.eishoncorp.algartech.app.modules.home.presentation.components.BodyCard
import com.eishoncorp.algartech.app.modules.home.presentation.components.FooterCard
import com.eishoncorp.algartech.app.modules.home.presentation.components.HeaderCard
import com.eishoncorp.algartech.app.modules.home.presentation.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@RequiresApi(Build.VERSION_CODES.Q)

@Composable
fun HomeScreen(
    navigate: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    // Contexto actual
    val context = LocalContext.current

    // Observando valores de ViewModel
    val findText: String by homeViewModel.findText.observeAsState("")
    val currentLocation: LatLng? by homeViewModel.currentLocation.observeAsState()
    val weather by homeViewModel.weather.observeAsState(null)
    val places by homeViewModel.places.observeAsState(emptyList())

    // Estado de animación del card
    var isExpanded by remember { mutableStateOf(false) }

    // Estado de posición de la cámara del mapa
    val cameraPositionState = rememberCameraPositionState {
        // Usar una ubicación predeterminada si `currentLocation` es null
        position = CameraPosition.fromLatLngZoom(currentLocation ?: LatLng(0.0, 0.0), 16f)
    }

    // Efecto lanzado cuando cambia la ubicación
    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(location, 16f))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            currentLocation?.let { location ->
                Marker(
                    state = MarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Tú ubicación actual",
                    draggable = true,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )
            }
        }

        CustomSearch(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 35.dp, start = 15.dp, end = 15.dp),
            text = findText,
            readOnly = false,
            suggestions = places,
            onValueChange = { homeViewModel.onSearchChange(it) },
            onSearch = { homeViewModel.fetchCurrentLocation() },
            onPlaceSelected = { homeViewModel.onPlaceSelected(it) },
            onClear = { homeViewModel.onClearText() }
        )

        ExpandableCard(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 110.dp, end = 15.dp),
            isExpanded = isExpanded, onToggle = {
                isExpanded = !isExpanded
            }) {
            weather?.let {
                HeaderCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    weather = weather,
                    context = context
                )
                BodyCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), weather = weather
                )
                FooterCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp),
                    weather = weather
                )
            }
        }

        FloatingActionButton(
            onClick = {
                homeViewModel.fetchCurrentLocation()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 18.dp, bottom = 27.dp),
            containerColor = colorResource(id = R.color.bottom_color),
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh Location",
                tint = colorResource(id = R.color.bottom_color_text)
            )
        }
    }
}