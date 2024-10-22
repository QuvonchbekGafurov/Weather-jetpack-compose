package com.example.weatherjetpackcompose.ui.Chart

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RainfallBarChart(rainfallPercentages:  List<String>, modifier: Modifier) {

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
        val timeList = listOf("2AM", "6AM", "10AM", "2PM", "6PM", "10PM")

        Log.e("TAG", "RainfallBarChart: ${rainfallPercentages.joinToString (",")}", ) // String qiymatlarni Float ga o'zgartirish va har bir barni chizish
        rainfallPercentages.forEachIndexed { index, percentage ->
            val floatValue = percentage.toFloatOrNull() ?: 0f  // Null holatlarni oldini olish
            val timeValue = timeList.getOrNull(index) ?: "N/A" // timeList dan mos qiymatni olish
            RainfallBar(floatValue, timeValue) // Bu yerda qiymatni Float sifatida va vaqtni yuboramiz
            Spacer(modifier = Modifier.height(8.dp)) // Barlar orasidagi bo'sh joy
        }
    }
}

@Composable
fun RainfallBar(percentage: Float,time:String) {
    val barColor = horizontalChcolor
    Row(modifier = Modifier) {
        Text(text = "$time",
            Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically))
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
        Text(text = "${percentage.toInt()}%", modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
            .fillMaxWidth(), // To'liq kenglikda bo'lishi uchun
            textAlign = TextAlign.Center)// Matn o'ng tomonga yopishtiriladi
    }
}
