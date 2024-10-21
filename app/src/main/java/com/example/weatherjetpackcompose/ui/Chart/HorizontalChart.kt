package com.example.weatherjetpackcompose.ui.Chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import com.example.weatherjetpackcompose.theme.horizontalChcolor
import com.example.weatherjetpackcompose.theme.mainback
import com.example.weatherjetpackcompose.ui.WeatherState.ImageInCircle
import com.example.weatherjetpackcompose.R

@Composable
fun RainfallBarChart(rainfallPercentages: List<Float>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TopBackgrounMain)
            .padding(16.dp),

        ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            ImageInCircle(image = R.drawable.rainy, imageSize = 20.dp, circleSize = 30.dp)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Chance of rain", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        rainfallPercentages.forEach { percentage ->
            RainfallBar(percentage)
            Spacer(modifier = Modifier.height(8.dp)) // Barlar orasidagi bo'sh joy
        }

    }
}

@Composable
fun RainfallBar(percentage: Float) {
    val barColor = horizontalChcolor
    Row(modifier = Modifier) {
        Text(text = "12 PM", Modifier.weight(1f).align(alignment = Alignment.CenterVertically))
        Box(
            modifier = Modifier
                .weight(5f)
                .clip(RoundedCornerShape(16.dp))
                .height(24.dp) // Barning balandligi
                .background(Color.White) // Orqa fon
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100) // Yomg'ir foiziga qarab kenglikni to'ldirish
                    .background(barColor) // Foizga qarab rang
            )
        }
        Text(text = "45%", modifier = Modifier.weight(1f).align(Alignment.CenterVertically) .fillMaxWidth(), // To'liq kenglikda bo'lishi uchun
            textAlign = TextAlign.Center)// Matn o'ng tomonga yopishtiriladi
    }
}
