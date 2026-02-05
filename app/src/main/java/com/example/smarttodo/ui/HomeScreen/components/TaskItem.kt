package com.example.smarttodo.ui.HomeScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.data.bean.TaskUi

@Composable
fun TaskItem(
    task: TaskUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onToggleCompletion: () -> Unit = {}
) {
    val contentAlpha = if (task.isCompleted) 0.4f else 1f

    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Status indicator (Clickable to toggle)
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (task.isCompleted)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = if (task.isCompleted) MaterialTheme.colorScheme.primary else priorityColor(task.priority),
                        shape = CircleShape
                    )
                    .clickable { onToggleCompletion() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(
                        onClick = {},
                        enabled = false,
                        label = {
                            Text(
                                text = if (task.isCompleted) "DONE"
                                else "${task.priority.name} PRIORITY",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.Transparent,
                            labelColor = if (task.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                priorityColor(task.priority)
                            },
                            disabledContainerColor = Color.Transparent,
                            disabledLabelColor = if (task.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                priorityColor(task.priority)
                            }
                        ),
                        border = AssistChipDefaults.assistChipBorder(
                            enabled = false,
                            borderColor = if (task.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                priorityColor(task.priority)
                            },
                            disabledBorderColor = if (task.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                priorityColor(task.priority)
                            }
                        )
                    )

                    if (task.timeLabel.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Date",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = task.timeLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }

                }
            }

            // Overflow
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun priorityColor(priority: Priority) = when (priority) {
    Priority.HIGH -> MaterialTheme.colorScheme.error
    Priority.MEDIUM -> MaterialTheme.colorScheme.tertiary
    Priority.LOW -> MaterialTheme.colorScheme.secondary
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskItemLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface {
            TaskItem(
                task = TaskUi(
                    title = "Finish UI Mockups",
                    priority = Priority.HIGH,
                    timeLabel = "Today",
                    isCompleted = false
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskItemDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface {
            TaskItem(
                task = TaskUi(
                    title = "Morning Yoga",
                    priority = Priority.LOW,
                    timeLabel = "DONE",
                    isCompleted = true
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}