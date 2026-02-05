package com.example.smarttodo.ui.addtask.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.smarttodo.R

@Composable
fun ScheduleCard(
    label: String = "Schedule",
    value: String = "Today, 5:00 PM",
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Leading icon
            Icon(
                painter = painterResource(R.drawable.calendar_vector),
                contentDescription = "Schedule",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(18.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Texts
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = Bold
                )
            }

            // Chevron
            Icon(
                painter = painterResource(R.drawable.rightarrow_vector),
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ScheduleCardLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        ScheduleCard()
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ScheduleCardDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        ScheduleCard()
    }
}