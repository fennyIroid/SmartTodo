package com.example.smarttodo.ui.planner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.data.local.entity.TaskEntity

@Composable
fun ScheduleItem(task: TaskEntity) {
    val accentColor = when (task.priority) {
        Priority.HIGH -> Color(0xFFF44336)
        Priority.MEDIUM -> Color(0xFF2196F3)
        Priority.LOW -> Color(0xFF4CAF50)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Time label
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.width(60.dp)
        ) {
            val timeParts = task.dueTime?.split(", ")?.getOrNull(2)?.split(" ") ?: listOf("10:00", "AM")
            Text(
                text = timeParts.getOrNull(0) ?: "10:00",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = timeParts.getOrNull(1) ?: "AM",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Vertical line logic would go here in a more complex canvas
        
        // Task Card
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.05f))
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                // Colored left accent
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(accentColor)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    task.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            maxLines = 1
                        )
                    }
                    
                    if (task.category.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(accentColor.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                // Mini avatar or icon placeholder
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "2 PARTICIPANTS", // Mock for design fidelity
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
