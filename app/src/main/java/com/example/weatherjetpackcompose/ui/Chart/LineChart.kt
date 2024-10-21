@file:Suppress("PackageDirectoryMismatch")

package com.example.weatherjetpackcompose.ui.Chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherjetpackcompose.theme.LineShadow
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import com.example.weatherjetpackcompose.theme.mainback
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry


@Composable
fun LineChart(modifier: Modifier) {
    val refreshDataSet = remember { mutableIntStateOf(0) }
    val modelProductor = remember { ChartEntryModelProducer() }
    val dataSetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
    val dataSetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }
    val weekDays =
        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val scrollState = rememberChartScrollState()
    // Qo'shimcha chiziq va markerlar qo'shish


    LaunchedEffect(key1 = refreshDataSet.intValue) {
        dataSetForModel.clear()
        dataSetLineSpec.clear()
        var xPos = 0f
        val dataPoints = arrayListOf<FloatEntry>()
        dataSetLineSpec.add(
            LineChart.LineSpec(
                lineColor = Color.Black.toArgb(), // Chiziq rangi qora
                lineBackgroundShader = DynamicShaders.fromBrush(
                    brush = Brush.verticalGradient(
                        listOf(
                            LineShadow.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                            Color.Green.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                        )
                    )
                ),
            )
        )




        for (i in 1..7) {
            val randomYFloat = (-10..10).random().toFloat()
            dataPoints.add(FloatEntry(x = xPos, y = randomYFloat))
            xPos += 1f
        }


        // Qo'shimcha ekstremal qiymatlar qo'shamiz
        dataPoints.add(FloatEntry(x = -1f, y = -10f)) // -10 qiymatini ko'rsatish uchun
        dataPoints.add(FloatEntry(x = 7f, y = 10f))  // 10 qiymatini ko'rsatish uchun

        dataSetForModel.add(dataPoints)
        modelProductor.setEntries(dataSetForModel)

    }

    // UI dizayn qismi
    Surface(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 8.dp).clip(RoundedCornerShape(16.dp)),
        color = mainback
    ) {
        Box(modifier = modifier) {
            // Chizma (graf) uchun karta
            Card(
                modifier = modifier
                    .align(Alignment.Center)
                    .background(mainback)

            ) {
                // Agar modelda ma'lumotlar bo'lsa, chizmalarni chizish
                if (dataSetForModel.isNotEmpty()) {
                    ProvideChartStyle {
                        Chart(
                            modifier = Modifier
                                .background(
                                    TopBackgrounMain
                                )
                                .padding(horizontal = 10.dp, vertical = 25.dp),
                            chart = LineChart(
                                lines = dataSetLineSpec,
                            ),
                            chartModelProducer = modelProductor,

                            startAxis = rememberStartAxis(

                                title = "Top values", // Y-ox uchun sarlavha
                                tickLength = 0.dp, // Belgi uzunligini o'rnatadi
                                valueFormatter = { value, _ ->
                                    when (value.toInt()) {
                                        -10, 0, 10 -> value.toInt().toString() + "\u00B0"
                                        else -> "" // Faqat -10, 0, va 10 ko'rinsin
                                    }
                                },
                                itemPlacer = AxisItemPlacer.Vertical.default( // Vertikal element joylashuvi
                                    maxItemCount = 3 // Ko‘rsatiladigan maksimal elementlar soni
                                ),
                            ),

                            bottomAxis = rememberBottomAxis(
                                title = "Count of values", // X-ox uchun sarlavha
                                tickLength = 0.dp, // Belgi uzunligini o'rnatadi
                                valueFormatter = { value, _ ->
                                    weekDays.getOrNull(value.toInt())
                                        ?: ""// Qiymatlarni 1 ga qo'shib formatlaydi
                                },
                                guideline = null // Yo‘nalish chiziqlari bo‘lmasligi uchun null o'rnatilgan
                            ),


                            chartScrollState = scrollState,
                            isZoomEnabled = true
                        )


                    }
                }
            }

            // Refresh tugmasi
            TextButton(
                onClick = { refreshDataSet.value++ } // Ma'lumotni yangilaydi
            ) {
                Text(text = "Refresh", textAlign = TextAlign.Center) // Tugma ichidagi matn
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun showLinechart() {
    LineChart(modifier = Modifier)
}