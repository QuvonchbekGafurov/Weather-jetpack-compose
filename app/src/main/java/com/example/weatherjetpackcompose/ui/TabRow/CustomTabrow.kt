package com.example.weatherjetpackcompose.ui.TabRow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherjetpackcompose.Selected
import com.example.weatherjetpackcompose.theme.tabrowClick


@Composable
fun CustomTabRow(
    modifier: Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Today", "Tomorrow", "10 days")
    Row(
        modifier = modifier
            .padding()
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tab ->
            TabButton(
                text = tab,
                isSelected = selectedTabIndex == index,
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(horizontal = 10.dp),
                onClick = { selectedTabIndex = index },
            )
        }
        Selected.isSelected.value=selectedTabIndex
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit,modifier: Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) tabrowClick else Color.White, // Selected color
            contentColor = Color.Black // Text color
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 0.dp)
        ,
        contentPadding = PaddingValues(0.dp) // Qo'shimcha paddingni olib tashlash

    ) {
        Text(text = text, textAlign = TextAlign.Center, fontSize = 14.sp, maxLines = 1, modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowTabrow() {
    CustomTabRow(modifier = Modifier)
}
