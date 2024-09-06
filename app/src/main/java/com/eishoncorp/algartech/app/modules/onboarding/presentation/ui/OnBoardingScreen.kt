package com.eishoncorp.algartech.app.modules.onboarding.presentation.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.eishoncorp.AlgarTech.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.eishoncorp.algartech.app.modules.onboarding.domain.model.pages
import com.eishoncorp.algartech.shared.utils.Dimentions
import com.eishoncorp.algartech.app.components.buttons.CustomButton
import com.eishoncorp.algartech.app.modules.onboarding.components.OnBoardingPage
import com.eishoncorp.algartech.app.components.pageIndicator.PagerIndicator
import com.eishoncorp.algartech.app.modules.onboarding.presentation.event.OnBoardingEvent
import com.eishoncorp.algartech.app.modules.onboarding.presentation.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onEvent: (OnBoardingEvent) -> Unit,
) {

    val onBoardingState by viewModel.onBoardingState.collectAsState()

    // ActivityResultLauncher para solicitar permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.updateLocationPermissionGranted(isGranted)
        if (isGranted) {
            onEvent(OnBoardingEvent.SaveAppEntry)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.input_background))
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonsState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Siguiente")
                    1 -> listOf("Atras", "Siguiente")
                    2 -> listOf("Atras", "Aceptar")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(
                modifier = Modifier.background(colorResource(id = R.color.input_background)),
                page = pages[index]
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimentions.MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PagerIndicator(
                modifier = Modifier.width(52.dp),
                pagesSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()

                if (buttonsState.value[0].isNotEmpty()) {
                    CustomButton(
                        text = buttonsState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage - 1
                                )
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                CustomButton(
                    text = buttonsState.value[1],
                    onClick = {
                        if (pagerState.currentPage == 2) {
                            // Solicitar el permiso a través del ViewModel
                            if (!onBoardingState.isLocationPermissionGranted) {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            } else {
                                onEvent(OnBoardingEvent.SaveAppEntry)
                            }
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}


