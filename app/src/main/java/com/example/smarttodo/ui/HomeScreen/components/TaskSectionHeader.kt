package com.example.smarttodo.ui.HomeScreen.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskSectionHeader(
    title: String,
    actionText: String,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = actionText,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskSectionHeaderLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        androidx.compose.material3.Surface {
            TaskSectionHeader(
                title = "My Tasks",
                actionText = "View All"
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskSectionHeaderDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        androidx.compose.material3.Surface {
            TaskSectionHeader(
                title = "My Tasks",
                actionText = "View All"
            )
        }
    }
}