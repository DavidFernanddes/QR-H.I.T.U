

package com.example.qr_hitu.screens.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val replyTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        fontSize = 57. sp,
        lineHeight = 64. sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        fontSize = 22. sp,
        lineHeight = 28. sp,
        letterSpacing = 0. sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        fontSize = 16. sp,
        lineHeight = 24. sp,
        letterSpacing = 0.15.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16. sp,
        lineHeight = 24. sp,
        letterSpacing = 0.15.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        fontSize = 12. sp,
        lineHeight = 16. sp,
        letterSpacing = 0.5.sp
    )
)