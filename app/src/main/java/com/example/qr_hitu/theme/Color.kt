package com.example.qr_hitu.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val background: Color,
    val primary: Color,
    val primaryContainer: Color,
    val primaryText: Color,
    val containerText: Color,
    val secondaryText: Color,
) {
    object Light : ThemeColors(
        background = Color(0xFFFFFFFF), //White 100
        primary = Color(0xFF4FC3F7), //Cyan 300
        primaryContainer = Color(0xFF03A9F4), //Cyan 500
        primaryText = Color(0xFFFFFFFF), //White 100
        containerText = Color(0xFFE1F5FE), //White 50
        secondaryText = Color(0xFF000000) //Black
    )
    object Dark : ThemeColors (
        background = Color(0xFF424242), // Dark Gray
        primary = Color(0xFF0988c3), //Cyan 700
        primaryContainer = Color(0xFF026897), //Cyan 800
        primaryText = Color(0xFF000000), //Black
        containerText = Color(0xFFFFFFFF), //Cyan 200
        secondaryText = Color(0xFFFFFFFF) //White 100
    )
}