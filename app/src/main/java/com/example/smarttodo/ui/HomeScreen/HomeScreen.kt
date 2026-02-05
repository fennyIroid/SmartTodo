package com.example.smarttodo.ui.HomeScreen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttodo.ui.HomeScreen.HomeViewModel
import com.example.smarttodo.ui.HomeScreen.toTaskUi
import com.example.smarttodo.ui.HomeScreen.components.HomeTopBar
import com.example.smarttodo.ui.HomeScreen.components.TaskItem
import com.example.smarttodo.ui.HomeScreen.components.TaskSectionHeader
import com.example.smarttodo.ui.home.components.TaskSummaryCard
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTaskClick: () -> Unit = {},
    onTaskClick: (Long) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()

    HomeContent(
        tasks = tasks,
        onAddTaskClick = onAddTaskClick,
        onTaskClick = onTaskClick,
        onToggleCompletion = { viewModel.toggleTaskCompletion(it) }
    )
}

@Composable
fun HomeContent(
    tasks: List<com.example.smarttodo.data.local.entity.TaskEntity>,
    onAddTaskClick: () -> Unit = {},
    onTaskClick: (Long) -> Unit = {},
    onToggleCompletion: (com.example.smarttodo.data.local.entity.TaskEntity) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {

                item {
                    HomeTopBar(userName = "Alex Johnson")
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val today = Calendar.getInstance()
                    val todayTasks = tasks.filter { 
                        it.dueTime == null || isSameDay(it.dueTime, today)
                    }
                    
                    TaskSummaryCard(
                        completedTasks = todayTasks.count { it.isCompleted },
                        totalTasks = todayTasks.size,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    TaskSectionHeader(
                        title = "My Tasks",
                        actionText = "View All"
                    )
                }

                items(tasks) { taskEntity ->
                    TaskItem(
                        task = taskEntity.toTaskUi(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        onClick = { onTaskClick(taskEntity.id) },
                        onToggleCompletion = { onToggleCompletion(taskEntity) }
                    )
                }
            }
        }
    }
}

private fun isSameDay(dueTime: String?, target: Calendar): Boolean {
    if (dueTime == null) return false
    if (dueTime.startsWith("Today")) return true // Simplified for UI toggle consistency
    
    return try {
        val parts = dueTime.split(", ")
        if (parts.size >= 2) {
            val datePart = parts[0] + ", " + parts[1]
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = sdf.parse(datePart)
            if (date != null) {
                val cal = Calendar.getInstance()
                cal.time = date
                cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                cal.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
            } else false
        } else false
    } catch (e: Exception) {
        false
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        HomeContent(tasks = emptyList())
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun HomeScreenDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        HomeContent(tasks = emptyList())
    }
}