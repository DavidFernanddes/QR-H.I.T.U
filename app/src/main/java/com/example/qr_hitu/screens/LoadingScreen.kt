package com.example.qr_hitu.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.theme.md_theme_light_primary

//TODO List and its contents
@Composable
fun LoadingScreen(navController: NavController){

    val animationProgress = remember { Animatable(-0.5f) }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
            ) {
        LaunchedEffect(Unit) {
            while (true) {
                animationProgress.animateTo(
                    targetValue = 0.5f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1500),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                animationProgress.snapTo(0f)
            }
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            val qrImageModifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)

            // Replace with your QR code image
            Image(
                painter = painterResource(R.drawable.qr_loading),
                contentDescription = "QR Code",
                modifier = Modifier.size(200.dp)
            )

            val qrImageHeightPx = with(LocalDensity.current) { 80.dp.roundToPx() }
            val barColor = md_theme_light_primary
            val barHeight = 5.dp
            val barWidth = 250.dp

            Box(
                modifier = Modifier
                    .width(barWidth)
                    .height(barHeight)
                    .offset(y = (animationProgress.value * qrImageHeightPx).dp)
                    .background(barColor, CircleShape)
            )

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "A Iniciar Sessão...", style = MaterialTheme.typography.titleMedium)
    }
}