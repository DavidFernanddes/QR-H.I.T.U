package com.example.qr_hitu.screens.adminScreens.tabLists

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfList
import com.example.qr_hitu.screens.adminScreens.tabLists.missingQrList.MissingQrList
import com.example.qr_hitu.screens.adminScreens.tabLists.recentScanList.RecentScanList

//  Tela com uma TopTab para dar acesso a cada uma das 3 listas da app
@Composable
fun TabLayout(navController: NavController, settingsManager: SettingsManager, viewModelSA: ScannerViewModel, viewModelMF: MalfunctionViewModel) {

    //  Estado da toptab
    var state by remember { mutableStateOf(0) }
    //  Titulos das tabs
    val titles = listOf(stringResource(R.string.malf), stringResource(R.string.recent), stringResource(R.string.missingQR))

    Column {
        TabRow(selectedTabIndex = state) {
            //  Cria os 3 titulos na toptab e verifica qual tem de estar ativo
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 1, style = MaterialTheme.typography.labelMedium) }
                )
            }
        }
        //  Condição para mostrar a lista selecionada
        when (state) {
            0 -> MalfList(
                navController = navController,
                viewModel = viewModelMF
            )
            1 -> RecentScanList(
                navController = navController,
                settingsManager = settingsManager,
                viewModel = viewModelSA
            )
            2 -> MissingQrList(
                navController = navController
            )
        }
    }
}