package com.example.smarttodo.ui.taskdetails

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.ui.theme.SmartTodoTheme

@Preview(showBackground = true)
@Composable
fun TaskDetailsLightPreview() {
    SmartTodoTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskDetailsContent(
                task = TaskEntity(
                    id = 1,
                    title = "Finish UI Mockups",
                    description = "Complete the final UI mockups for the project, ensuring all elements are pixel-perfect and aligned with the design specifications.",
                    priority = Priority.HIGH,
                    category = "Work",
                    dueTime = "Oct 24"
                ),
                onBack = {},
                onEdit = {},
                onComplete = {},
                onDelete = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsDarkPreview() {
    SmartTodoTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskDetailsContent(
                task = TaskEntity(
                    id = 1,
                    title = "Finish UI Mockups",
                    description = "Complete the final UI mockups for the project, ensuring all elements are pixel-perfect and aligned with the design specifications.",
                    priority = Priority.HIGH,
                    category = "Work",
                    dueTime = "Oct 24"
                ),
                onBack = {},
                onEdit = {},
                onComplete = {},
                onDelete = {}
            )
        }
    }
}
