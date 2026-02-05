package com.example.smarttodo.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TaskSummaryCard(
    completedTasks: Int,
    totalTasks: Int,
    modifier: Modifier = Modifier
) {
    val progress =
        if (totalTasks == 0) 0f else completedTasks.toFloat() / totalTasks

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Left content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tasks for today",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You have $completedTasks of $totalTasks tasks completed",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Progress bar background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
                        )
                ) {
                    // Progress bar foreground
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Percentage badge
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "TaskSummaryCard – Light",
    showBackground = true
)
@Composable
fun TaskSummaryCardPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        androidx.compose.material3.Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TaskSummaryCard(
                completedTasks = 3,
                totalTasks = 8,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    name = "TaskSummaryCard – Dark",
    showBackground = true
)
@Composable
fun TaskSummaryCardDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        androidx.compose.material3.Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TaskSummaryCard(
                completedTasks = 3,
                totalTasks = 8,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}