package com.example.weatherjetpackcompose.ui.WeatherState

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import com.example.weatherjetpackcompose.Selected
import com.example.weatherjetpackcompose.modelapi.WeatherData
import com.example.weatherjetpackcompose.ui.Days.Today
import com.example.weatherjetpackcompose.ui.Days.WeatherList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabContentSwitcher(modifier: Modifier,weatherData: WeatherData) {
    var weatherDatagson by remember { mutableStateOf<WeatherData?>(null) }
    weatherDatagson=weatherData
    Log.e("TAG", "TabContentSwitcher: $weatherDatagson", )
    Column(modifier=modifier.layoutId("body")) {
        val value=Selected.isSelected.value
        when (Selected.isSelected.value) {
            0 -> Today(0)
            1 -> Today(1)
            2 -> WeatherList(weatherData = weatherDatagson!!)
        }
        Log.e("TAG", "TabContentSwitcher: $value", )
    }
}