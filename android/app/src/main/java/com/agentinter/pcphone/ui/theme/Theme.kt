package com.agentinter.pcphone.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = Secondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    error = Error,
    onError = androidx.compose.ui.graphics.Color.White,
    background = BackgroundLight,
    onBackground = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
    surface = SurfaceLight,
    onSurface = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE8EAED),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF5F6368)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = androidx.compose.ui.graphics.Color(0xFF003D7A),
    secondary = SecondaryLight,
    onSecondary = androidx.compose.ui.graphics.Color(0xFF003B33),
    error = ErrorDark,
    onError = androidx.compose.ui.graphics.Color(0xFF690005),
    background = BackgroundDark,
    onBackground = androidx.compose.ui.graphics.Color(0xFFE6E1E5),
    surface = SurfaceDark,
    onSurface = androidx.compose.ui.graphics.Color(0xFFE6E1E5),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF2A2A3E),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF9AA0A6)
)

@Composable
fun AgentInterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
