package com.example.smarttodo.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttodo.ui.theme.SageGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigationHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1200)
        onNavigationHome()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // App Icon Container
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SageGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = "SmartTask",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Divider
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(2.dp)
                    .background(SageGreen)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tagline
            Text(
                text = "ORGANIZE. FOCUS. FINISH.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                letterSpacing = 1.5.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        onNavigationHome = {}
    )
}

