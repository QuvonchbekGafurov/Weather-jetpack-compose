package com.example.weatherjetpackcompose.ui.WeatherState

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherjetpackcompose.R
import com.example.weatherjetpackcompose.modelapi.Astro


@Composable
fun Sunset(astro:Astro) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.sunrise,
                label = "Sunrise",
                mainValue = "${astro.sunrise}",

            )
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.sunset,
                label = "Sunset",
                mainValue = "${astro.sunset}",

            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.nights_stay,
                label = "Moonrise",
                mainValue = "${astro.moonrise}",

            )
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.moonset,
                label = "Moonset",
                mainValue = "${astro.moonset}",

            )
        }
    }
}