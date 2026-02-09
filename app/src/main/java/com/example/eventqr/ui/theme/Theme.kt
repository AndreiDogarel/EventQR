package com.example.eventqr.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = BrandIndigo,
    onPrimary = Color.White,
    secondary = BrandCyan,
    onSecondary = Color(0xFF001018),
    tertiary = BrandTeal,
    onTertiary = Color(0xFF00110F),

    background = Color(0xFFF6F7FB),
    onBackground = Color(0xFF0B1220),

    surface = Color.White,
    onSurface = Color(0xFF0B1220),
    surfaceVariant = Color(0xFFE8ECF7),
    onSurfaceVariant = Color(0xFF334155),

    error = Color(0xFFEF4444),
    onError = Color.White
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = BrandCyan,
    onPrimary = Color(0xFF001018),
    secondary = BrandIndigo,
    onSecondary = Color.White,
    tertiary = BrandTeal,
    onTertiary = Color(0xFF00110F),

    background = DarkBg,
    onBackground = Color(0xFFE5E7EB),

    surface = DarkSurface,
    onSurface = Color(0xFFE5E7EB),
    surfaceVariant = DarkSurface2,
    onSurfaceVariant = Color(0xFFB6C2D1),

    error = Color(0xFFFCA5A5),
    onError = Color(0xFF2B0B0B)
)

@Composable
fun EventQRTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
