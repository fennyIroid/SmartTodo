package com.example.smarttodo.ui.addtask.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskTextInputSection(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Title input
        TextField(
            value = title,
            onValueChange = onTitleChange,
            placeholder = {
                Column() {
                    Text(
                        text = "What’s the plan?",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add details and notes...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            },
            textStyle = MaterialTheme.typography.headlineLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskTextInputLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        TaskTextInputSection(
            title = "Preview Title",
            onTitleChange = {}
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TaskTextInputDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        TaskTextInputSection(
            title = "Preview Title",
            onTitleChange = {}
        )
    }
}