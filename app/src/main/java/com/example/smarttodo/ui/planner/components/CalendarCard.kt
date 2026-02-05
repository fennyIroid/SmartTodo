package com.example.smarttodo.ui.planner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun CalendarCard(
    selectedDate: Calendar,
    daysWithTasks: Set<String> = emptySet(),
    onDateSelected: (Calendar) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Days of Week Header
            Row(modifier = Modifier.fillMaxWidth()) {
                val days = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                days.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar Grid Calculation
            val monthCalendar = Calendar.getInstance().apply {
                time = selectedDate.time
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val firstDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
            val daysInMonth = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            
            val totalCells = ((daysInMonth + firstDayOfWeek + 6) / 7) * 7
            val calendarDays = (1..totalCells).map { i ->
                val dayNum = i - firstDayOfWeek
                if (dayNum in 1..daysInMonth) dayNum else null
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.wrapContentHeight(),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(calendarDays) { day ->
                    day?.let {
                        val isSelected = it == selectedDate.get(Calendar.DAY_OF_MONTH)
                        
                        // Check if this date has tasks
                        val itemDate = (selectedDate.clone() as Calendar).apply {
                            set(Calendar.DAY_OF_MONTH, it)
                        }
                        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        val dateKey = sdf.format(itemDate.time)
                        val hasTasks = daysWithTasks.contains(dateKey)

                        DayItem(
                            day = it,
                            isSelected = isSelected,
                            hasTasks = hasTasks,
                            onClick = {
                                onDateSelected(itemDate)
                            }
                        )
                    } ?: Spacer(modifier = Modifier.aspectRatio(1f))
                }
            }
        }
    }
}

@Composable
fun DayItem(
    day: Int,
    isSelected: Boolean,
    hasTasks: Boolean,
    dotColor: Color = Color(0xFF2196F3), // Default blue
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (isSelected) Color(0xFF91A37F) else Color.Transparent,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
        
        Box(modifier = Modifier.height(8.dp), contentAlignment = Alignment.TopCenter) {
            if (hasTasks) {
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(4.dp)
                        .background(dotColor, CircleShape)
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "CalendarCard - Light",
    showBackground = true
)
@Composable
fun CalendarCardLightPreview() {
    val today = Calendar.getInstance()

    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        CalendarCard(
            selectedDate = today,
            onDateSelected = {}
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "CalendarCard - Dark",
    showBackground = true
)
@Composable
fun CalendarCardDarkPreview() {
    val today = Calendar.getInstance()

    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        CalendarCard(
            selectedDate = today,
            onDateSelected = {}
        )
    }
}
