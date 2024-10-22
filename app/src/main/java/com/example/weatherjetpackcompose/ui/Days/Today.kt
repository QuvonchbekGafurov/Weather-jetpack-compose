package com.example.weatherjetpackcompose.ui.Days

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.android.volley.NoConnectionError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request.Method
import com.android.volley.TimeoutError
import com.example.weatherjetpackcompose.modelapi.Astro
import com.example.weatherjetpackcompose.modelapi.Day
import com.example.weatherjetpackcompose.modelapi.Hour
import com.example.weatherjetpackcompose.modelapi.WeatherData
import com.example.weatherjetpackcompose.theme.mainback
import com.example.weatherjetpackcompose.ui.Hourly.HourlyForecast
import com.example.weatherjetpackcompose.ui.Chart.LineChart
import com.example.weatherjetpackcompose.ui.Chart.RainfallBarChart
import com.example.weatherjetpackcompose.ui.WeatherState.Sunset
import com.example.weatherjetpackcompose.ui.WeatherState.WeatherStats
import com.example.weatherjetpackcompose.ui.localdata.astroData
import com.example.weatherjetpackcompose.ui.localdata.dailyWeather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.launch
import loadWeatherDataFromAssets
import org.json.JSONObject
import saveWeatherDataToStorage
import kotlin.math.log

@Composable
fun Today(
    Day: Int
) {
    var city by remember { mutableStateOf("Loading...") }
    var hourlyTemperature by remember { mutableStateOf<List<Hour>?>(null) }
    var current by remember { mutableStateOf<Day?>(null) }
    var astro by remember { mutableStateOf<Astro?>(null) }
    var mutableTimeList = remember { mutableStateListOf<String>() }

    var weatherDatagson by remember { mutableStateOf<WeatherData?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current
    weatherDatagson = loadWeatherDataFromAssets(context)
    Log.e("TAG", "Today: $weatherDatagson")
    val scope = rememberCoroutineScope() // Scope yaratamiz

    // Joylashuvni olish
    LaunchedEffect(Unit) {


        getLocation(context = context) { response ->
            city = response
            Log.e("TAG", "Location:$response ")
        }
        // Ob-havo ma'lumotlarini olish
        getData("Tashkent", context) { weatherData ->
            if (weatherData != null) {
                Log.e("TAG", "Qalampir: $weatherData")

                hourlyTemperature = weatherData!!.forecast.forecastday[Day].hour
                Log.e("TAG", "Today22:${hourlyTemperature} ")

                current = weatherData!!.forecast.forecastday[Day].day
                astro = weatherData!!.forecast.forecastday[Day].astro
                saveWeatherDataToStorage(context, weatherData)
                isLoading = false // Yuklash tugadi
                Log.d("MyLog", "Current Temperature: $hourlyTemperature")
            } else {
                isLoading = false // Yuklash tugadi
                hourlyTemperature = weatherDatagson!!.forecast.forecastday[Day].hour
                current = weatherDatagson!!.forecast.forecastday[Day].day
                astro = weatherDatagson!!.forecast.forecastday[Day].astro
                Log.d("MyLog", "Failed to get weather data.")


            }
        }

    }
    Box(
        modifier = Modifier
            .alpha(if (isLoading) 0.3f else 1f)
            .fillMaxWidth()
            .background(mainback)
    ) {
        LaunchedEffect(isLoading) {
            if (!isLoading) {
                // Ma'lumot kelganidan keyin ishchi for loop'ni ishga tushiradi
                scope.launch {
                    hourlyTemperature?.let { hourlyTemps ->
                        for (i in 2..23 step 4) {
                            val chanceOfRain = hourlyTemps.getOrNull(i)?.chance_of_rain
                            if (chanceOfRain != null) {
                                mutableTimeList.add(chanceOfRain.toString())
                                Log.e("TAG", "Today1: ${hourlyTemps[i].time}")
                            }
                        }
                    }
                    Log.e("TAG", "Today122: $mutableTimeList", )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                WeatherStats(current1 = current ?: dailyWeather)
                HourlyForecast(hourlyTemperature ?: emptyList())
                LineChart(modifier = Modifier)
                Log.e("TAG", "Today4444: ${mutableTimeList.joinToString (",")}", )
                RainfallBarChart(
                    mutableTimeList,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .background(mainback)
                        .clip(RoundedCornerShape(16.dp)),

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Sunset(astro ?: astroData)
            }


        }
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center, // O'rtaga joylashtirish
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color.Red // Loading indikatorining rangi qizil
                )
            }
        }
    }

}


fun getLocation(context: Context, onLocationRetrieved: (String) -> Unit) {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // Check location permission
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
        return
    }

    // Get latitude and longitude
    val location = fusedLocationProviderClient.lastLocation
    location.addOnSuccessListener {
        if (it != null) {
            var textLatitude = "Latitude: " + it.latitude.toString()
            val textLongitude = "Longitude: " + it.longitude.toString()
            Log.e("location", "getLocation: $textLongitude   $textLatitude")
            var city = "$textLatitude," + "$textLongitude"
            onLocationRetrieved(city)
        } else onLocationRetrieved("London")
    }
}

fun getData(city: String, context: Context, onResult: (WeatherData?) -> Unit) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=a0330f401b594ef7a1a100525241310" +
            "&q=$city" +
            "&days=10" +
            "&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Method.GET,
        url,
        { response ->
            val weatherData = parseResponse(response)
            onResult(weatherData)
            Log.d("MyLog", "Response: $response")
        },
        { error ->
            when (error) {
                is TimeoutError -> {
                    Log.e("MyLog", "Error: Timeout. Internet sekin.")
                    Toast.makeText(context, "Internet sekin. Iltimos, kuting.", Toast.LENGTH_LONG)
                        .show()
                }

                is NoConnectionError -> {
                    Log.e("MyLog", "Error: No internet connection.")
                    Toast.makeText(context, "Internet aloqasi yo'q.", Toast.LENGTH_LONG).show()
                }

                else -> {
                    Log.e(
                        "MyLog",
                        "VolleyError: ${error.networkResponse?.statusCode ?: "No Response"}"
                    )
                }
            }
            onResult(null)
        }
    )

    queue.add(sRequest)

}

private fun parseResponse(response: String): WeatherData? {
    return try {
        val jsonObject = JSONObject(response)
        val gson = Gson()
        gson.fromJson(jsonObject.toString(), WeatherData::class.java)
    } catch (e: Exception) {
        Log.e("MyLog", "Error parsing response: ${e.message}")
        null
    }
}