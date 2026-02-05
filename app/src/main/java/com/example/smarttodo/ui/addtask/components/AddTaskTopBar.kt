package com.example.smarttodo.ui.addtask.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskTopBar(
    title: String = "New Task",
    onCancelClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    isDoneEnabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Cancel
        TextButton(onClick = onCancelClick) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        // Done
        Button(
            onClick = onDoneClick,
            enabled = isDoneEnabled,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "Done",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskTopBarLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface {
            AddTaskTopBar()
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskTopBarDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface {
            AddTaskTopBar()
        }
    }
}