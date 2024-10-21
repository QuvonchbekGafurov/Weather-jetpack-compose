package com.example.weatherjetpackcompose.ui.Hourly

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import com.example.weatherjetpackcompose.ui.WeatherState.ImageInCircle
import com.example.weatherjetpackcompose.R
import com.example.weatherjetpackcompose.modelapi.Current
import com.example.weatherjetpackcompose.modelapi.Hour
@Composable
fun HourlyForecast(hourlytemp:List<Hour>) {
    var city by remember { mutableStateOf("Loading...") }
    var hourlyTemperature by remember { mutableStateOf<List<Hour>?>(null) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFEDE7F6),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        hourlyTemperature=hourlytemp

        // UI qismi
        Column(
            modifier = Modifier
                .background(TopBackgrounMain)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ImageInCircle(image = R.drawable.history_toggle_off, imageSize = 20.dp, circleSize = 30.dp)
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Hourly forecast")
            }

            // Ma'lumotlarni yuklashni tekshirish
            if (hourlyTemperature != null) {
                LazyRow(
                    Modifier.padding(10.dp)
                ) {
                    items(hourlyTemperature!!.size) { index ->
                        val hour = hourlyTemperature!![index]
                        val iconUrl = "https:${hour.condition.icon}"
                        Log.e("TAG", "HourlyForecast: $hour $iconUrl")

                        val timeText = when {
                            index == 0 -> "12 AM"
                            index < 12 -> "$index AM"
                            else -> "${index - 12} PM"
                        }

                        HourlyForecastCard(
                            text1 = timeText,
                            img = rememberAsyncImagePainter(iconUrl),
                            modifier = Modifier.weight(1f),
                            text2 =" ${hourlyTemperature!![index]?.temp_c?.toInt()}",

                        )
                    }
                }
            } else {
                // Ma'lumotlar yuklanayotgan bo'lsa, yuklanish holati
                Text(text = "Loading hourly forecast...")
            }
        }
    }
}

@Composable
fun HourlyForecastCard(
    text1: String,
    img: AsyncImagePainter,
    modifier: Modifier,
    text2:String,
) {
    Column(
        modifier = modifier
            .background(TopBackgrounMain)
            .padding(horizontal = 10.dp)
    ) {
        Text(text = text1)
        Image(
            painter = img,
            contentDescription = "Weather icon",
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop
        )
         Text(text = "${text2}\u00B0", fontWeight = FontWeight.Bold)

    }
}
