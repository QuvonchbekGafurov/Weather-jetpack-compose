import android.content.Context
import com.example.weatherjetpackcompose.modelapi.WeatherData
import com.google.gson.Gson
import java.io.IOException

fun saveWeatherDataToStorage(context: Context, weatherData: WeatherData) {
    // Gson orqali weatherData ni JSON formatiga aylantirish
    val jsonString = Gson().toJson(weatherData)

    // Ma'lumotlarni faylga yozish
    context.openFileOutput("weather_data.json", Context.MODE_PRIVATE).use { output ->
        output.write(jsonString.toByteArray())
    }
}

// JSON faylni o'qish uchun funksiya
fun loadWeatherDataFromAssets(context: Context): WeatherData? {
    val json: String?
    try {
        // `assets` papkasidan faylni o'qish
        json = context.assets.open("weather_data.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }

    // Gson yordamida JSONni obyektga aylantirish
    return Gson().fromJson(json, WeatherData::class.java)
}