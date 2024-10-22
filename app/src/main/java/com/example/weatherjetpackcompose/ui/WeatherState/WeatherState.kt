package com.example.weatherjetpackcompose.ui.WeatherState
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import com.example.weatherjetpackcompose.theme.mainback
import com.example.weatherjetpackcompose.R
import com.example.weatherjetpackcompose.modelapi.Current
import com.example.weatherjetpackcompose.modelapi.Day
import com.example.weatherjetpackcompose.modelapi.Hour
import kotlin.math.log

@Composable
fun WeatherInfoCard(
    modifier: Modifier,
    Image:Int,
    label: String,
    mainValue: String,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFEDE7F6), // Orqa fon rangini o'zgartirishingiz mumkin
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(mainback)
    ) {
        Row(
            modifier = Modifier
                .background(TopBackgrounMain)
                .padding(10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageInCircle(image =Image, imageSize = 20.dp, circleSize = 30.dp)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = label, fontSize = 14.sp, color = Color.Black, maxLines = 1)
                Spacer(modifier = Modifier.padding(top = 6.dp))
                Text(text = mainValue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

        }
    }
}

@Composable
fun WeatherStats(current1: Day,hourlytemp:List<Hour>) {
    var hourlyTemperature by remember { mutableStateOf<List<Hour>?>(null) }

    var current by remember { mutableStateOf<Day?>(null) }
    current=current1
    hourlyTemperature=hourlytemp
    var pressure by remember { mutableStateOf<Int>(1000) }
    if (hourlyTemperature != null) {
        if (hourlyTemperature!!.isNotEmpty()) {
            pressure = hourlyTemperature!![0].pressure_mb.toInt()
            Log.e("TAG", "WeatherStats122: $pressure", )
        }
    } else {
        // Handle the case where the list is empty
        pressure = 1000 // or set to a default value, or handle accordingly
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //hourlyTemperature=hourlytemp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.air,
                label = "Wind speed",
                mainValue = "${current!!.maxtemp_c.toInt()}km/h",

            )

            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.light_mode,
                label = "UV Index",
                mainValue = "${current!!.uv}",

            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.waves,
                label = "Pressure",
                mainValue = "$pressure hpa",

            )
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.rainy,
                label = "Rain Chance",
                mainValue = "${current!!.daily_chance_of_rain}%",
            )
        }
    }
}

@Composable
fun ImageInCircle(
    image: Int,  // Rasmni parametr sifatida qabul qilamiz
    imageSize: Dp = 32.dp,  // Rasmning o'lchamini ham parametr qilamiz
    circleSize: Dp = 64.dp  // Aylananing o'lchamini parametr qilamiz
) {
    Box(
        modifier = Modifier
            .size(circleSize)  // Aylananing o'lchami
            .background(
                color = Color.White,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center  // Rasmni o'rtada joylashtirish
    ) {
        Image(
            painterResource(id = image),  // Funksiya orqali berilgan rasmni ko'rsatamiz
            contentDescription = "Image in Circle",
            modifier = Modifier.size(imageSize)  // Rasmingizning o'lchami
        )
    }
}
