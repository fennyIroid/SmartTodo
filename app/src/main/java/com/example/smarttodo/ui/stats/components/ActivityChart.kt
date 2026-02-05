package com.example.smarttodo.ui.stats.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ActivityChart(
    weeklyActivity: List<Int>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Activity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Last 7 Days",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = weeklyActivity.sum().toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "TOTAL TASKS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bar Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val maxActivity = weeklyActivity.maxOrNull()?.coerceAtLeast(1) ?: 1
                
                // Dynamically generate day labels (S, M, T, W, T, F, S)
                val labels = rememberDayLabels()
                
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barWidth = 14.dp.toPx()
                    val spacing = (size.width - (barWidth * 7)) / 8
                    
                    weeklyActivity.forEachIndexed { index, activity ->
                        val barHeight = (activity.toFloat() / maxActivity) * (size.height - 40.dp.toPx())
                        val x = spacing + index * (barWidth + spacing)
                        val y = size.height - barHeight - 30.dp.toPx()
                        

                        val color =  Color(0xFFFFD8BE)
                        drawRoundRect(
                            color = color,
                            topLeft = Offset(x, y),
                            size = Size(barWidth, barHeight.coerceAtLeast(4.dp.toPx())),
                            cornerRadius = CornerRadius(barWidth / 2)
                        )
                    }
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    labels.forEachIndexed { index, day ->
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (index == 6) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontWeight = if (index == 6) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberDayLabels(): List<String> {
    return androidx.compose.runtime.remember {
        val list = mutableListOf<String>()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("E", Locale.getDefault())
        for (i in 0..6) {
            val label = sdf.format(cal.time).first().toString()
            list.add(label)
            cal.add(Calendar.DAY_OF_YEAR, -1)
        }
        list.reversed()
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ActivityChartLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface {
            ActivityChart(
                weeklyActivity = listOf(2, 4, 1, 5, 3, 6, 2)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ActivityChartDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface {
            ActivityChart(
                weeklyActivity = listOf(1, 0, 3, 2, 4, 1, 5)
            )
        }
    }
}
