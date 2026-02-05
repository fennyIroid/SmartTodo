package com.example.smarttodo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smarttodo.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconRes: Int? = null,
    val icon: ImageVector? = null
) {
    object Home : BottomNavItem(Routes.HOME, "Tasks", icon = Icons.Default.Home)
    object Planner : BottomNavItem(Routes.CALENDAR, "Planner", icon = Icons.Default.DateRange)
    object Stats : BottomNavItem(Routes.STATS, "Stats", iconRes = R.drawable.stats_vector)
    object Settings : BottomNavItem(Routes.PROFILE, "Settings", icon = Icons.Default.Person)
}
