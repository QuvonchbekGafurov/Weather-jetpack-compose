import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.compose.ui.layout.layoutId
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherjetpackcompose.R
import com.example.weatherjetpackcompose.modelapi.Astro
import com.example.weatherjetpackcompose.modelapi.Day
import com.example.weatherjetpackcompose.modelapi.Hour
import com.example.weatherjetpackcompose.modelapi.WeatherData
import com.example.weatherjetpackcompose.theme.TopBackgrounMain
import com.example.weatherjetpackcompose.theme.mainback
import com.example.weatherjetpackcompose.ui.Days.getData
import com.example.weatherjetpackcompose.ui.Days.getLocation
import com.example.weatherjetpackcompose.ui.TabRow.CustomTabRow
import com.example.weatherjetpackcompose.ui.WeatherState.TabContentSwitcher

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)
@Composable
fun ScrollableMotionLayout() {
    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
    var city by remember { mutableStateOf("Loading...") }
    val context = LocalContext.current

    var weatherDatagson by remember { mutableStateOf<WeatherData?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    weatherDatagson = loadWeatherDataFromAssets(LocalContext.current)
    LaunchedEffect(Unit) {


        getLocation(context = context) { response ->
            city = response
            Log.e("TAG", "Location:$response ",)
        }
        // Ob-havo ma'lumotlarini olish
        getData("Tashkent", context) { weatherData ->
            if (weatherData != null) {
                Log.e("KUKU", "Qalampir: $weatherData",)
                weatherDatagson=weatherData
                saveWeatherDataToStorage(context, weatherData)
                isLoading = false // Yuklash tugadi
            } else {
                Log.d("KUKU", "Failed to get weather data.")
                isLoading = false // Yuklash tugadi
            }
        }
    }
    BoxWithConstraints( // To get the max height
        modifier = Modifier
            .fillMaxSize()
            .background(mainback)
    ) {


        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    return if (delta < 0) {
                        swipingState.performDrag(delta).toOffset()
                    } else {
                        Offset.Zero
                    }
                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    return swipingState.performDrag(delta).toOffset()
                }


                override suspend fun onPostFling(
                    consumed: androidx.compose.ui.unit.Velocity,
                    available: androidx.compose.ui.unit.Velocity
                ): androidx.compose.ui.unit.Velocity {
                    swipingState.performFling(velocity = available.y)
                    return super.onPostFling(consumed, available)
                }

                private fun Float.toOffset() = Offset(0f, this)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.05f)
                    },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,
                        heightInPx to SwipingStates.EXPANDED,
                    )
                )
                .nestedScroll(nestedScrollConnection)
        ) {
            val computedProgress by remember {
                derivedStateOf {
                    if (swipingState.progress.to == SwipingStates.COLLAPSED)
                        swipingState.progress.fraction
                    else
                        1f - swipingState.progress.fraction
                }
            }
            val startHeightNum = 400
            val endHeightNum = 200

            MotionLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainback),
                start = ConstraintSet {
                    val box1 = createRefFor("box1")
                    val body = createRefFor("body")
                    val custom=createRefFor("customtabrow")
                    constrain(box1) {
                        this.width = Dimension.matchParent
                        this.height =
                            Dimension.value(startHeightNum.dp) // Box1 height 500.dp startda
                    }
                    constrain(body) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.fillToConstraints
                        this.top.linkTo(custom.bottom, 0.dp) // Box1 ostida joylashadi, 0dp masofa
                        this.bottom.linkTo(parent.bottom, 0.dp)
                    }
                    constrain(custom) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.value(60.dp)
                        this.top.linkTo(box1.bottom, 0.dp) // Box1 ostida joylashadi, 0dp masofa
                    }

                },
                end = ConstraintSet {
                    val box1 = createRefFor("box1")
                    val body = createRefFor("body")
                    val custom=createRefFor("customtabrow")

                    constrain(box1) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.value(120.dp) // Box2 height 200.dp endda
                    }
                    constrain(body) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.fillToConstraints
                        this.top.linkTo(custom.bottom, 0.dp) // Box2 ostida joylashadi, 0dp masofa
                        this.bottom.linkTo(parent.bottom, 0.dp)
                    }
                    constrain(custom){
                        this.width = Dimension.matchParent
                        this.height=Dimension.value(60.dp)
                        this.top.linkTo(box1.bottom, 0.dp) // Box2 ostida joylashadi, 0dp masofa
                    }

                },
                progress = computedProgress,
            ) {
                // Box1 - asta-sekin 300.dp ga qadar qisqaradi va 300.dp ga yetganda yo'qoladi
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("box1")
                        .height(
                            when {
                                computedProgress >= 0.5f -> ((400 - (250 - endHeightNum) * (computedProgress - 0.5f) * 3.33f).dp) // Balandlik faqat 0.5 dan keyin hisoblanadi
                                else -> 0.dp  // 300.dp dan 200.dp gacha qisqaradi
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img), // Tasvir manbai
                        contentDescription = "",
                        modifier = Modifier
                            .background(if (computedProgress <= 0.5f) mainback else TopBackgrounMain)
                            .layoutId("background")
                            .alpha(
                                when {
                                    computedProgress <= 0.5f -> 1f - computedProgress * 1.33f // 0.7 va 0.8 oralig'ida sekin yo'qoladi
                                    else -> 0f // 300dp ga yetganda yo'qoladi
                                }
                            )
                            .fillMaxSize()
                            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "${weatherDatagson?.location?.name},"+"${weatherDatagson?.location?.country}",
                        color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 20.sp
                    )
                    Column(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val iconUrl = "https:${weatherDatagson?.current?.condition?.icon}"
                        Image(
                            painter = rememberAsyncImagePainter(iconUrl), // Tasvir manbai
                            contentDescription = "",
                            modifier = Modifier
                                .size(if (computedProgress <= 0.5f) 120.dp else 60.dp)
                                .layoutId("Icon"),
                            contentScale = ContentScale.Fit,
                        )
                        Text(
                            text = "${weatherDatagson?.current?.condition?.text}",
                            color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                            modifier = Modifier
                                .padding(10.dp),
                            fontSize = if (computedProgress <= 0.5f) 18.sp else 13.sp,
                        )
                    }


                    Row(
                        modifier = Modifier
                            .align(alignment = if (computedProgress <= 0.5f) Alignment.CenterStart else Alignment.BottomStart)
                            .padding(start = 20.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${weatherDatagson?.current?.temp_c?.toInt()}\u00B0",
                            color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                            fontSize = if (computedProgress <= 0.5f) 90.sp else 60.sp,
                            letterSpacing = if (computedProgress <= 0.5f) -5.sp else -1.sp,
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                        Text(

                            text = "Fells like ${weatherDatagson?.current?.feelslike_c?.toInt()}°",
                            color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                            modifier = Modifier
                            .padding(bottom = if (computedProgress <= 0.5f) 30.dp else 15.dp),
                            fontSize = if (computedProgress <= 0.5f) 18.sp else 13.sp,
                        )
                    }

                    Text(
                        text = "${weatherDatagson?.location?.localtime}",
                        color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                        modifier = Modifier
                            .alpha(
                                when {
                                    computedProgress <= 0.5f -> 1f - computedProgress * 1.33f // 0.7 va 0.8 oralig'ida sekin yo'qoladi
                                    else -> 0f // 300dp ga yetganda yo'qoladi
                                }
                            )
                            .align(Alignment.BottomStart)
                            .padding(start = 25.dp, bottom = 25.dp),
                        fontSize = 18.sp,
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Day ${weatherDatagson?.forecast?.forecastday?.get(0)?.day?.maxtemp_c?.toInt()}°",
                            color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                            modifier = Modifier
                                .alpha(
                                    when {
                                        computedProgress <= 0.5f -> 1f - computedProgress * 1.33f // 0.7 va 0.8 oralig'ida sekin yo'qoladi
                                        else -> 0f // 300dp ga yetganda yo'qoladi
                                    }
                                ),
                            fontSize = 18.sp,
                        )
                        Text(
                            text = "Night ${weatherDatagson?.forecast?.forecastday?.get(0)?.day?.mintemp_c?.toInt()}°",
                            color = if (computedProgress <= 0.5f) Color.White else Color.Black,
                            modifier = Modifier.alpha(
                                    when {
                                        computedProgress <= 0.5f -> 1f - computedProgress * 1.33f // 0.7 va 0.8 oralig'ida sekin yo'qoladi
                                        else -> 0f // 300dp ga yetganda yo'qoladi
                                    }
                                    ),
                            fontSize = 18.sp,
                        )
                    }

                }

                CustomTabRow(
                    modifier = Modifier
                        .layoutId("customtabrow")
                        .background(if (computedProgress <= 0.999f) mainback else TopBackgrounMain)

                )
                TabContentSwitcher(  modifier = Modifier, weatherData =weatherDatagson!! )
            }


        }
    }
}



