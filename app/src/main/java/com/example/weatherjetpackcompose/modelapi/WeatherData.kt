package com.example.weatherjetpackcompose.modelapi

data class WeatherData(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)