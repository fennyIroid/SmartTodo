package com.example.smarttodo.ui.addtask.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarttodo.R
import com.example.smarttodo.ui.theme.AmberGold
import com.example.smarttodo.ui.theme.EmeraldGreen

@Composable
fun AddTaskFooter(
    modifier: Modifier = Modifier,
    isCreateEnabled: Boolean = true,
    buttonText: String = "Create Task",
    repeat: String = "None",
    onRepeatClick: () -> Unit = {},
    isReminderEnabled: Boolean = false,
    onReminderToggle: (Boolean) -> Unit = {},
    onCreateTaskClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // Repeat row
        FooterRow(
            painter = painterResource(R.drawable.repeat_vector),
            title = "Repeat",
            value = repeat,
            iconTint = AmberGold,
            onClick = onRepeatClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reminder row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.alarm_vector),
                contentDescription = "Reminders",
                tint = EmeraldGreen,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Reminders",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = isReminderEnabled,
                onCheckedChange = onReminderToggle
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Create Task button
        Button(
            onClick = onCreateTaskClick,
            enabled = isCreateEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor =
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor =
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


@Composable
private fun FooterRow(
    painter: Painter,
    title: String,
    value: String,
    iconTint: Color,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
        Icon(
            painter = painter,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.rightarrow_vector),
                contentDescription = "more",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskFooterLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddTaskFooter(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskFooterDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddTaskFooter(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}