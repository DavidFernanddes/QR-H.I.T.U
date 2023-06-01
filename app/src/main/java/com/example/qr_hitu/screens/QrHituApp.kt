package com.example.qr_hitu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.functions.ScaffoldLayouts
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.theme.QRHITUTheme

@Composable
fun QrHituApp() {
    val navController = rememberNavController()
    val viewModel1 = viewModel<ViewModel1>()
    val viewModel2 = viewModel<ViewModel2>()
    val viewModelSA = viewModel<ScannerViewModel>()
    val viewModelMF = viewModel<MalfunctionViewModel>()

    ScaffoldLayouts(navController, viewModel1, viewModel2, viewModelSA, viewModelMF)
}
