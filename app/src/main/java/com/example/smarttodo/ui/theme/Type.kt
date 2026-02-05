package com.example.smarttodo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.smarttodo.R

// Primary (Serif – for elegance, headings): Playfair Display
val SerifFontFamily = FontFamily(
    Font(R.font.playfair_display_semibold, FontWeight.SemiBold),
    Font(R.font.playfair_display_bold, FontWeight.Bold)
)

// Secondary (Sans-Serif – for clarity, body/UI): Inter
val SansSerifFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = SerifFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = (-0.25).sp,
        lineHeight = 40.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = SerifFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SansSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.4.sp
    )
)