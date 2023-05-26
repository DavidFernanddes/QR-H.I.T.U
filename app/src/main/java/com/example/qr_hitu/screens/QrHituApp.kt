package com.example.qr_hitu.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.functions.ScaffoldLayouts
import com.example.qr_hitu.viewModels.ScannerViewModel
import com.example.qr_hitu.viewModels.ViewModel1
import com.example.qr_hitu.viewModels.ViewModel2

@Composable
fun QrHituApp() {
    val navController = rememberNavController()
    val viewModel1 = viewModel<ViewModel1>()
    val viewModel2 = viewModel<ViewModel2>()
    val viewModelSA = viewModel<ScannerViewModel>()

    ScaffoldLayouts(navController, viewModel1, viewModel2, viewModelSA)
}