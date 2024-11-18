package com.example.weatheradvisor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatheradvisor.R

@Composable
fun ClothingAdviceUI(temperature: Double, modifier: Modifier) {
    val clothingImage = getClothingImageBasedOnTemperature(temperature)

    Image(
        painter = painterResource(id = clothingImage),
        contentDescription = "Clothing suggestion",
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 150.dp)
    )
}

fun getClothingImageBasedOnTemperature(temperature: Double): Int {
    return when {
        temperature < 0 -> R.drawable.ic_heavy_coat
        temperature in 0.0..10.0 -> R.drawable.ic_light_coat_and_hat
        temperature in 10.0..20.0 -> R.drawable.ic_light_jacket
        temperature > 20 -> R.drawable.ic_tshirt
        else -> R.drawable.ic_heavy_coat
    }
}