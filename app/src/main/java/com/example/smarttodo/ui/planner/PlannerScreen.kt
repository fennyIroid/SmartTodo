package com.example.smarttodo.ui.planner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttodo.ui.planner.components.CalendarCard
import com.example.smarttodo.ui.planner.components.PlannerHeader
import com.example.smarttodo.ui.planner.components.ScheduleItem

@Composable
fun PlannerScreen(
    onAddTaskClick: () -> Unit,
    viewModel: PlannerViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasksForSelectedDate.collectAsState()
    val daysWithTasks by viewModel.daysWithTasks.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlannerHeader(
                currentDate = viewModel.selectedDate,
                onAddClick = onAddTaskClick
            )

            CalendarCard(
                selectedDate = viewModel.selectedDate,
                daysWithTasks = daysWithTasks,
                onDateSelected = viewModel::onDateSelected
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Today's Schedule",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Surface(
                    color = Color(0xFFE8EDE3), // Light sage
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${tasks.size} TASKS",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF91A37F),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                if (tasks.isEmpty()) {
                    item {
                        Text(
                            text = "No tasks for this day",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    items(tasks) { task ->
                        ScheduleItem(task = task)
                    }
                }
            }
        }
    }
}
