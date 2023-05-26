package com.example.qr_hitu.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.functions.ScaffoldLayouts
import com.example.qr_hitu.viewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2

@Composable
fun QrHituApp() {
    val navController = rememberNavController()
    val viewModel1 = viewModel<ViewModel1>()
    val viewModel2 = viewModel<ViewModel2>()
    val viewModelSA = viewModel<ScannerViewModel>()
    val viewModelMF = viewModel<MalfunctionViewModel>()

    ScaffoldLayouts(navController, viewModel1, viewModel2, viewModelSA, viewModelMF)
}