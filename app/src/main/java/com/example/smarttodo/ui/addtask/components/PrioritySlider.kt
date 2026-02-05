package com.example.smarttodo.ui.addtask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun PrioritySlider(
    priority: com.example.smarttodo.data.bean.Priority,
    onPriorityChange: (com.example.smarttodo.data.bean.Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    val sliderValue = when (priority) {
        com.example.smarttodo.data.bean.Priority.LOW -> 0f
        com.example.smarttodo.data.bean.Priority.MEDIUM -> 1f
        com.example.smarttodo.data.bean.Priority.HIGH -> 2f
    }
    val isDark = isSystemInDarkTheme()

    val priorityText = when (priority) {
        com.example.smarttodo.data.bean.Priority.LOW -> "Low"
        com.example.smarttodo.data.bean.Priority.MEDIUM -> "Medium"
        com.example.smarttodo.data.bean.Priority.HIGH -> "High"
    }

    val activeColor = if (isDark) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    val inactiveColor = MaterialTheme.colorScheme.onBackground.copy(
        alpha = if (isDark) 0.25f else 0.2f
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PRIORITY",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = priorityText,
                style = MaterialTheme.typography.labelMedium,
                color = activeColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = sliderValue,
            onValueChange = { 
                val newPriority = when (it.roundToInt()) {
                    0 -> com.example.smarttodo.data.bean.Priority.LOW
                    1 -> com.example.smarttodo.data.bean.Priority.MEDIUM
                    else -> com.example.smarttodo.data.bean.Priority.HIGH
                }
                onPriorityChange(newPriority)
            },
            valueRange = 0f..2f,
            steps = 1,
            colors = SliderDefaults.colors(
                thumbColor = activeColor,
                activeTrackColor = activeColor,
                inactiveTrackColor = inactiveColor
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Low",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "High",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun PrioritySliderLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        PrioritySlider(
            priority = com.example.smarttodo.data.bean.Priority.MEDIUM,
            onPriorityChange = {}
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun PrioritySliderDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        PrioritySlider(
            priority = com.example.smarttodo.data.bean.Priority.MEDIUM,
            onPriorityChange = {}
        )
    }
}