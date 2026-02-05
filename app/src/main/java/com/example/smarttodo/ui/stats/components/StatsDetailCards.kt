package com.example.smarttodo.ui.stats.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smarttodo.R

@Composable
fun StatsDetailCards(
    streak: Int,
    focusTime: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        SmallStatsCard(
            modifier = Modifier.weight(1f),
            label = "CURRENT STREAK",
            value = "$streak Days",
            iconRes = R.drawable.calendar_vector,
            iconColor = Color(0xFFA58FD8) // Purple from design
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        SmallStatsCard(
            modifier = Modifier.weight(1f),
            label = "FOCUS TIME",
            value = "$focusTime hrs",
            iconRes = R.drawable.alarm_vector, // Changed from category_vector
            iconColor = Color(0xFF81D4FA) // Blue from design
        )
    }
}

@Composable
fun SmallStatsCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    iconRes: Int,
    iconColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                // Colored icon logic or colored background circle
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun StatsDetailCardsLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface {
            StatsDetailCards(
                streak = 7,
                focusTime = 12.5f
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun StatsDetailCardsDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface {
            StatsDetailCards(
                streak = 3,
                focusTime = 2.8f
            )
        }
    }
}
