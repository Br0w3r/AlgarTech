package com.eishoncorp.algartech.app.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eishoncorp.AlgarTech.R
import com.eishoncorp.AlgarTech.ui.theme.AlgarTechTheme

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    disabled: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = !disabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (disabled) colorResource(id = R.color.input_disable_background) else colorResource(
                id = R.color.bottom_color
            ),
            contentColor = colorResource(id = R.color.input_background)
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.bottom_color_text)
            ),
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ButtonsBoardingPreview() {
    AlgarTechTheme {
        CustomButton(
            text = "Hola",
            onClick = {
            }
        )
    }
}

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick, shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.bottom_color)
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.bottom_color_text)
            ),
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BoardingTextButtonPreview() {
    AlgarTechTheme {
        CustomTextButton(
            text = "Hola",
            onClick = {
            }
        )
    }
}