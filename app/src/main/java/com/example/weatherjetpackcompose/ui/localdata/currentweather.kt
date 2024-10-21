package com.example.weatherjetpackcompose.ui.localdata

import com.example.weatherjetpackcompose.modelapi.Astro
import com.example.weatherjetpackcompose.modelapi.Condition
import com.example.weatherjetpackcompose.modelapi.Current
import com.example.weatherjetpackcompose.modelapi.Day

val currentWeather = Current(
    cloud = 30,
    condition = Condition(text = "Sunny", icon = "sun.png", code = 1000),
    dewpoint_c = 5.0,
    dewpoint_f = 41.0,
    feelslike_c = 25.0,
    feelslike_f = 77.0,
    gust_kph = 10.0,
    gust_mph = 6.2,
    heatindex_c = 28.0,
    heatindex_f = 82.4,
    humidity = 60,
    is_day = 1,
    last_updated = "2024-10-18 10:00",
    last_updated_epoch = 1729240800,
    precip_in = 0.0,
    precip_mm = 0.0,
    pressure_in = 30.05,
    pressure_mb = 1017.0,
    temp_c = 22.0,
    temp_f = 71.6,
    uv = 5.0,
    vis_km = 10.0,
    vis_miles = 6.0,
    wind_degree = 180,
    wind_dir = "S",
    wind_kph = 12.0,
    wind_mph = 7.5,
    windchill_c = 18.0,
    windchill_f = 64.4
)
val dailyWeather = Day(
    avghumidity = 65,
    avgtemp_c = 22.0,
    avgtemp_f = 71.6,
    avgvis_km = 10.0,
    avgvis_miles = 6.0,
    condition = Condition(text = "Sunny", icon = "sun.png", code = 1000),
    daily_chance_of_rain = 20,
    daily_chance_of_snow = 0,
    daily_will_it_rain = 0,
    daily_will_it_snow = 0,
    maxtemp_c = 25.0,
    maxtemp_f = 77.0,
    maxwind_kph = 15.0,
    maxwind_mph = 9.3,
    mintemp_c = 18.0,
    mintemp_f = 64.4,
    totalprecip_in = 0.0,
    totalprecip_mm = 0.0,
    totalsnow_cm = 0.0,
    uv = 5.0
)

val astroData = Astro(
    sunrise = "06:38 AM",
    sunset = "05:38 PM",
    moonrise = "05:57 PM",
    moonset = "07:33 AM",
    moon_phase = "Waning Gibbous",
    moon_illumination = 100,
    is_moon_up = 1,  // Oy ko'rinadi
    is_sun_up = 0    // Quyosh ko'rinmayapti
)
