package com.example.weatherjetpackcompose.ui.WeatherState
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

@Composable
fun WeatherInfoCard(
    modifier: Modifier,
    Image:Int,
    label: String,
    mainValue: String,
    subValue: String,
    subValueColor: Color
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
fun WeatherStats(current1: Day) {
    var current by remember { mutableStateOf<Day?>(null) }
    current=current1
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.air,
                label = "Wind speed",
                mainValue = "${current!!.maxtemp_c.toInt()}km/h",
                subValue = "2 km/h",
                subValueColor = Color.Red
            )

            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.light_mode,
                label = "UV Index",
                mainValue = "${current!!.uv}",
                subValue = "0.3",
                subValueColor = Color.Red
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
                mainValue = "${current!!.daily_chance_of_snow}",
                subValue = "32 hpa",
                subValueColor = Color.Magenta
            )
            WeatherInfoCard(
                Modifier.weight(1f),
                Image = R.drawable.rainy,
                label = "Rain Chance",
                mainValue = "${current!!.daily_chance_of_rain}%",
                subValue = "10%",
                subValueColor = Color.Magenta
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
