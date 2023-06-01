package com.example.qr_hitu.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    background = ThemeColors.Light.background,
    primary = ThemeColors.Light.primary,
    onPrimary = ThemeColors.Light.primaryText,
    primaryContainer = ThemeColors.Light.primaryContainer,
    onPrimaryContainer = ThemeColors.Light.containerText,
    onSecondary = ThemeColors.Light.secondaryText
)
private val DarkColors = darkColorScheme(
    background = ThemeColors.Dark.background,
    primary = ThemeColors.Dark.primary,
    onPrimary = ThemeColors.Dark.primaryText,
    primaryContainer = ThemeColors.Dark.primaryContainer,
    onPrimaryContainer = ThemeColors.Dark.containerText,
    onSecondary = ThemeColors.Dark.secondaryText
)


@Composable
fun QRHITUTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit, theme: String) {

    MaterialTheme(
        content = content,
        shapes = replyShapes,
        typography = replyTypography,
        colorScheme = when(theme) {
            "Light" -> LightColors
            "Dark" -> DarkColors
            else -> if (isDarkTheme) DarkColors else LightColors
        }
    )
}