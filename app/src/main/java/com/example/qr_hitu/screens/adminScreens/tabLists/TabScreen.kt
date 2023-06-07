package com.example.qr_hitu.screens.adminScreens.tabLists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfList
import com.example.qr_hitu.screens.adminScreens.tabLists.missingQrList.MissingQrList
import com.example.qr_hitu.screens.adminScreens.tabLists.recentScanList.RecentScanList

@Composable
fun TabLayout(navController: NavController, settingsManager: SettingsManager, viewModelSA: ScannerViewModel, viewModelMF: MalfunctionViewModel) {

    var state by remember { mutableStateOf(0) }
    val titles = listOf(stringResource(R.string.malf), stringResource(R.string.recent), stringResource(R.string.missingQR))
    Column {
        TabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 1, style = MaterialTheme.typography.labelMedium) }
                )
            }
        }
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