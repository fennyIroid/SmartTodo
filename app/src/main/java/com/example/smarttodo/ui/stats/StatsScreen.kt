package com.example.smarttodo.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttodo.ui.stats.components.ActivityChart
import com.example.smarttodo.ui.stats.components.StatsDetailCards
import com.example.smarttodo.ui.stats.components.TodayFocusCard

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val completionRate by viewModel.completionRate.collectAsState()
    val finishedTasks by viewModel.finishedTasksCount.collectAsState()
    val totalTasks by viewModel.totalTasksCount.collectAsState()
    val weeklyActivity by viewModel.weeklyActivity.collectAsState()
    val currentStreak by viewModel.currentStreak.collectAsState()
    val focusTime by viewModel.focusTime.collectAsState()
    val achievements by viewModel.achievements.collectAsState()

    StatsContent(
        completionRate = completionRate,
        finishedTasks = finishedTasks,
        totalTasks = totalTasks,
        weeklyActivity = weeklyActivity,
        currentStreak = currentStreak,
        focusTime = focusTime,
        achievements = achievements
    )
}

@Composable
fun StatsContent(
    completionRate: Float,
    finishedTasks: Int,
    totalTasks: Int,
    weeklyActivity: List<Int>,
    currentStreak: Int,
    focusTime: Float,
    achievements: List<Achievement>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Back */ }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                }
                
                Text(
                    text = "Productivity Stats",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(onClick = { /* Share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }

            TodayFocusCard(
                completionRate = completionRate,
                finishedCount = finishedTasks,
                totalCount = totalTasks
            )

            Spacer(modifier = Modifier.height(24.dp))

            StatsDetailCards(
                streak = currentStreak,
                focusTime = focusTime
            )

            Spacer(modifier = Modifier.height(24.dp))

            ActivityChart(
                weeklyActivity = weeklyActivity
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Achievements section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { /* View All */ }) {
                    Text("View All", color = Color(0xFF91A37F))
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal row for achievements (simple flow row or row with scroll)
            // Just displaying the first few for now
            if (achievements.isEmpty()) {
                Text(
                    text = "Complete tasks to unlock achievements!",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            } else {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    achievements.forEach { achievement ->
                        AchievementItem(achievement)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Just a placeholder to ensure the layout matches the scrollable nature
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun AchievementItem(achievement: Achievement) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFF6E5), RoundedCornerShape(8.dp)), // Soft gold background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB300) // Amber gold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "Stats Screen - Light",
    showBackground = true
)
@Composable
fun StatsScreenLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        StatsContent(
            completionRate = 0.75f,
            finishedTasks = 6,
            totalTasks = 8,
            weeklyActivity = listOf(3, 5, 2, 6, 4, 7, 1),
            currentStreak = 4,
            focusTime = 2.5f,
            achievements = listOf(
                Achievement("First Steps", "Completed your first task!"),
                Achievement("On Fire", "3 day streak!")
            )
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "Stats Screen - Dark",
    showBackground = true
)
@Composable
fun StatsScreenDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        StatsContent(
            completionRate = 0.42f,
            finishedTasks = 4,
            totalTasks = 10,
            weeklyActivity = listOf(1, 3, 2, 4, 5, 3, 2),
            currentStreak = 12,
            focusTime = 5.0f,
            achievements = listOf(
                Achievement("Productivity Master", "Completed 10 tasks.")
            )
        )
    }
}
