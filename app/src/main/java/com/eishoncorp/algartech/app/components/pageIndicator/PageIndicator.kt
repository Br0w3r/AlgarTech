package com.eishoncorp.algartech.app.components.pageIndicator

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import com.eishoncorp.AlgarTech.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eishoncorp.AlgarTech.ui.theme.AlgarTechTheme
import com.eishoncorp.algartech.shared.utils.Dimentions
import com.eishoncorp.algartech.app.modules.onboarding.domain.model.pages

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = colorResource(id = R.color.bottom_color),
    unselectedColor: Color = colorResource(id = R.color.shimmer),
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(Dimentions.IndicatorSize)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PagerIndicatorPreview() {
    AlgarTechTheme {
        PagerIndicator(
            modifier = Modifier.width(52.dp),
            pagesSize = pages.size,
            selectedPage = 0
        )
    }
}