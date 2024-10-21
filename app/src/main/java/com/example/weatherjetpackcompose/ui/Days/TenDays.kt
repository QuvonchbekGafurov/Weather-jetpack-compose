package com.example.weatherjetpackcompose.ui.Days

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherjetpackcompose.modelapi.Day
import com.example.weatherjetpackcompose.modelapi.Forecast
import com.example.weatherjetpackcompose.modelapi.Forecastday
import com.example.weatherjetpackcompose.modelapi.WeatherData
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun WeatherCard(
    day: String,
    weatherDescription: String,
    maxTemp: String,
    minTemp: String,
    image: String
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = TopBackgrounMain
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = day, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = weatherDescription, fontSize = 14.sp, color = Color.Gray)
            }

            Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                Column(

                ) {
                    Text(text = "${maxTemp}°", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$minTemp°", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.padding(end = 10.dp))
                // Vertikal chiziq
                Divider(
                    modifier = Modifier
                        .height(60.dp) // chiziqning balandligi
                        .width(1.dp) // chiziqning kengligi
                        .background(Color.Blue) // chiziqning rangi
                    ,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))

                val url = "https:" + image
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherList(weatherData: WeatherData) {
    var date by remember { mutableStateOf<String?>(null) }
    var days by remember { mutableStateOf<Forecastday?>(null) }
    Log.e("TAG", "WeatherList:$weatherData ", )
    val size=weatherData?.forecast?.forecastday!!.size

    LazyColumn(Modifier.padding(10.dp)) {
        items(size) { index ->
            days=weatherData?.forecast?.forecastday!![index]
            date=weatherData?.forecast?.forecastday!![index]?.date
            val year=date!!.substring(0,4).toInt()
            val month=date!!.substring(5,7).toInt()
            val day=date!!.substring(8,10).toInt()
            val formateDate= formatDate(year,month,day)
            WeatherCard(
                day = "$formateDate",
                weatherDescription = "${days?.day?.condition?.text}",
                maxTemp = "${ days?.day?.maxtemp_c?.toInt() }",
                minTemp = "${ days?.day?.mintemp_c?.toInt() }",
                image = "${days?.day?.condition?.icon}"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(year: Int,month: Int,day: Int): String {
    val date = LocalDate.of(year, month, day) // Sana yaratish
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale("uz")) // Sana formati
    return date.format(formatter).split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } } // Formatlangan sana
}
