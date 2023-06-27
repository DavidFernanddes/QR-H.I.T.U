@file:Suppress("UNUSED_PARAMETER")

package com.example.qr_hitu.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.functions.SettingsManager


//  Tela de loading enquanto faz log in
@Composable
fun LoadingScreen(navController: NavController, settingsManager: SettingsManager, isDarkTheme: Boolean = isSystemInDarkTheme()){

    //  Estado com o progresso da animação
    val animationProgress = remember { Animatable(-0.5f) }
    //  Verificações do tema para definir qual imagem usar no loading
    val switch = remember { mutableStateOf("") }
    val theme by rememberUpdatedState(
        if (switch.value == "") {
            settingsManager.getSetting("Theme", "")
        } else switch.value
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //  Abre Coroutine
        LaunchedEffect(Unit) {
            //  A animação funciona apenas enquanto estiver na tela
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
            //  Condição do tema
            when (theme) {
                "Light" -> Image(
                    painter = painterResource(R.drawable.qr_loading_light),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(200.dp)
                )
                "Dark" -> Image(
                    painter = painterResource(R.drawable.qr_loading_dark),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(200.dp)
                )
                else -> if (isDarkTheme) Image(
                    painter = painterResource(R.drawable.qr_loading_dark),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(200.dp)
                ) else Image(
                    painter = painterResource(R.drawable.qr_loading_light),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(200.dp)
                )
            }

            //  Variáveis com o aspeto da animação
            val qrImageHeightPx = with(LocalDensity.current) { 80.dp.roundToPx() }
            val barColor = MaterialTheme.colorScheme.primary
            val barHeight = 5.dp
            val barWidth = 250.dp

            //  Linha animada
            Box(
                modifier = Modifier
                    .width(barWidth)
                    .height(barHeight)
                    .offset(y = (animationProgress.value * qrImageHeightPx).dp)
                    .background(barColor, CircleShape)
            )

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = stringResource(R.string.loading), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)
    }
}