package com.eishoncorp.algartech.app.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.algartech.core.data.services.googlePlaces.models.PredictionPlace
import com.eishoncorp.algartech.shared.utils.Dimentions.IconSize


@Composable
fun CustomSearch(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    suggestions: List<PredictionPlace>,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onPlaceSelected: (PredictionPlace) -> Unit,
    onClear: () -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onSearch()
        }
    }

    Box(modifier = modifier) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .searchBar(),
                value = text,
                onValueChange = onValueChange,
                readOnly = readOnly,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.size(IconSize),
                        tint = colorResource(id = R.color.bottom_color)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize)
                            .clickable {
                                onClear()
                            },
                        tint = colorResource(id = R.color.bottom_color)
                    )
                },
                placeholder = {
                    Text(
                        text = "Ingresa una ciudad",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.placeholder)
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.input_background),
                    unfocusedContainerColor = colorResource(id = R.color.input_background),
                    focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch()
                    }
                ),
                textStyle = MaterialTheme.typography.bodySmall,
                interactionSource = interactionSource
            )

            // Mostrar la lista de sugerencias
            if (suggestions.isNotEmpty()) {
                suggestions.forEach { place ->
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.input_background))
                            .padding(top = 8.dp)

                    ) {
                        Text(
                            text = place.description,
                            color = colorResource(id = R.color.body),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onPlaceSelected(place) },
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.searchBar(): Modifier = composed {
    if (!isSystemInDarkTheme()) {
        border(
            width = 0.5.dp,
            color = colorResource(id = R.color.body),
            shape = MaterialTheme.shapes.small
        )
    } else {
        this
    }
}
