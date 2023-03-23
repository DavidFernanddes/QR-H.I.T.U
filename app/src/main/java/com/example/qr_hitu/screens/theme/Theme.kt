package com.example.qr_hitu.screens.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer
)
private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
)


@Composable
fun QRHITUTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colorSchemecolors = lightColorScheme()
    /*
    val colorSchemecolors =
        if (!useDarkTheme) {
            lightColorScheme()
        } else {
            darkColorScheme()
        }

     */
    MaterialTheme(
        colorScheme = colorSchemecolors,
        content = content,
        shapes = replyShapes,
        typography = replyTypography
    )
}