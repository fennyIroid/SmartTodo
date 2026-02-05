package com.example.smarttodo.ui.planner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun PlannerHeader(
    currentDate: Calendar,
    onSearchClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    val monthName = currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    val year = currentDate.get(Calendar.YEAR)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = monthName ?: "",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = year.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            
            Spacer(modifier = Modifier.width(12.dp))

            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF91A37F), // Sage green from design
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    }
}
