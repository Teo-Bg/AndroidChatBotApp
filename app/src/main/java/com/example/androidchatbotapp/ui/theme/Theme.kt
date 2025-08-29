package com.example.androidchatbotapp.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    surface = surface,
    onSurface = onSurface,
    outlineVariant = outlineVariant,
    onSurfaceVariant = onSurfaceVariant
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window
    if (window != null) {
        window.statusBarColor = LightColors.surface.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        window.navigationBarColor = LightColors.surface.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
    }

    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
