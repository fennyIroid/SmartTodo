package com.example.smarttodo.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColors = lightColorScheme(
    primary = SageGreen,
    onPrimary = PureWhite,

    secondary = SoftPeach,
    onSecondary = DarkCharcoal,

    background = CreamWhite,
    onBackground = DarkCharcoal,

    surface = PureWhite,
    onSurface = DarkCharcoal,

    error = ClayTerracotta,
    outline = SoftSand
)

private val DarkColors = darkColorScheme(
    primary = EmeraldGreen,
    onPrimary = OffWhite,

    secondary = ElectricIndigo,
    onSecondary = OffWhite,

    background = DeepCharcoal,
    onBackground = OffWhite,

    surface = SlateGrey,
    onSurface = OffWhite,

    error = VividRed,
    outline = MutedSlate
)

@Composable
fun SmartTodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}