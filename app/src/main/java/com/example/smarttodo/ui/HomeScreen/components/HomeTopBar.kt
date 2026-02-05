package com.example.smarttodo.ui.HomeScreen.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarttodo.R
import com.example.smarttodo.ui.theme.SmartTodoTheme

@Composable
fun HomeTopBar(
    userName: String,
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Left text section
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Welcome back,",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Search icon
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(18.dp)
            )
        }

        // Filter icon
        IconButton(onClick = onFilterClick) {
            Icon(
                painter = painterResource(id = R.drawable.filter_vector),
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Preview(
    name = "HomeTopBar – Light",
    showBackground = true
)
@Composable
fun HomeTopBarPreview() {
    SmartTodoTheme(darkTheme = false) {
        HomeTopBar(
            userName = "Alex Johnson"
        )
    }
}

@Preview(
    name = "HomeTopBar – Dark",
    showBackground = true
)
@Composable
fun HomeTopBarDarkPreview() {
    SmartTodoTheme(darkTheme = true) {
        androidx.compose.material3.Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            HomeTopBar(
                userName = "Alex Johnson"
            )
        }
    }
}