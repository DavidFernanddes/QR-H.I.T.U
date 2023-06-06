package com.example.qr_hitu.functions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.components.*
import com.example.qr_hitu.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ScaffoldLayouts(navController: NavController, settingsManager: SettingsManager, viewModel1: ViewModel1, viewModel2: ViewModel2, viewModelSA : ScannerViewModel, viewModelMF : MalfunctionViewModel, switch: MutableState<String>){

    val localContext = LocalContext
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val destinationRoute = currentDestination?.route ?: Login.route

    Scaffold(
        topBar = {
            when {
                destinationRoute.contains(ScannerAdminInfoUpdate.route) ->  TopBar1(navController = navController)
                destinationRoute.contains(Create1.route) || destinationRoute.contains(ChooseQr.route) -> TopBar5(navController = navController)
                destinationRoute.contains(Create2.route) -> TopBar2(navController = navController)
                destinationRoute.contains(Create3.route) || destinationRoute.contains(TransferQr.route) -> TopBar3(navController = navController)
                destinationRoute.contains(ScanInput.route) -> TopBarUser3(navController = navController)
                destinationRoute.contains(SettingOptions.route) || destinationRoute.contains(Manual.route) || destinationRoute.contains(MalfInfo.route) -> TopBarUni(navController = navController)
                destinationRoute.contains(ScannerAdminInfo.route) -> TopBar4(navController = navController, viewModelSA)
                destinationRoute.contains(MalfList.route) || destinationRoute.contains(ScanAdmin.route) || destinationRoute.contains(AdminChoices.route) || destinationRoute.contains(RecentScanList.route) -> TopBar1(navController = navController)
                destinationRoute.contains(UserChoices.route) -> TopBarUser1(navController = navController)
                destinationRoute.contains(ScanProf.route) || destinationRoute.contains(MQRLocal.route) -> TopBarUser2(navController = navController)
            }
        },
        bottomBar = {
            when{
                destinationRoute.contains(MalfList.route) || destinationRoute.contains(ScanAdmin.route) || destinationRoute.contains(AdminChoices.route) || destinationRoute.contains(RecentScanList.route) -> BottomBar(navController = navController)
            }
        },
        scaffoldState = scaffoldState,
    ) { innerPadding ->
            QrHituNavHost(
                navController = navController,
                settingsManager = settingsManager,
                viewModel1 = viewModel1,
                viewModel2 = viewModel2,
                viewModelSA = viewModelSA,
                viewModelMF = viewModelMF,
                switch = switch,
                modifier = Modifier.padding(innerPadding)
            )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar1(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            MenuOptions(navController = navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar2(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar3(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.navigate(MalfList.route) }) {
                Icon(Icons.Filled.Close ,"Close", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar4(navController: NavController, viewModel: ScannerViewModel){
    val email = emailString()

    val showState = remember { mutableStateOf(false) }
    val show by rememberUpdatedState(showState.value)
    val (block, room, machine) = viewModel.myData.value.toString().split(",")

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            MenuOptions(navController = navController)
        },
        actions = {

            IconButton(onClick = { navController.navigate(ScannerAdminInfoUpdate.route) }) {
                Icon(Icons.Filled.Edit, "Edit", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            IconButton(onClick = { showState.value = true }) {
                Icon(Icons.Filled.Delete, "Delete", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        },
    )
    if(show){
        DelDialog(onDialogDismissed = { showState.value = false}, onDeleteClick = { delDispositivo(block, room, machine); navController.navigate(MalfList.route) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar5(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            MenuOptions(navController = navController)
        },
        actions = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser1(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
           MenuOptions(navController = navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser2(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            MenuOptions(navController = navController)
        },
        actions = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser3(navController: NavController){
    val email = emailString()

    TopAppBar(
        title = { Text(text = email, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            MenuOptions(navController = navController)
        },
        actions = {
            IconButton(onClick = { navController.navigate(UserChoices.route) }) {
                Icon(Icons.Filled.Close ,"Close", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUni(navController: NavController){

    TopAppBar(
        title = { Text(text = "", color = MaterialTheme.colorScheme.onPrimaryContainer) },
        colors = topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )

}


@Composable
fun BottomBar(navController: NavController){

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == MalfList.route,
                    label = { Text(text = stringResource(R.string.list), color = MaterialTheme.colorScheme.onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = { navController.navigate(MalfList.route) },
                    icon = {
                        Icon(Icons.Filled.FormatListBulleted, "Avarias")
                    }
                )
                NavigationBarItem(
                    selected =
                    navController.currentBackStackEntry?.destination?.route == RecentScanList.route,
                    label = { Text(text = "Recentes", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = { navController.navigate(RecentScanList.route) },
                    icon = {
                        Icon(Icons.Filled.QrCode, "Scanner")
                    }
                )
                NavigationBarItem(
                    selected =
                            navController.currentBackStackEntry?.destination?.route == ScanAdmin.route ||
                                navController.currentBackStackEntry?.destination?.route == ScannerAdminInfo.route ||
                                    navController.currentBackStackEntry?.destination?.route == ScannerAdminInfoUpdate.route,
                    label = { Text(text = stringResource(R.string.scanner), color = MaterialTheme.colorScheme.onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = { navController.navigate(ScanAdmin.route) },
                    icon = {
                        Icon(Icons.Filled.QrCodeScanner, "Scanner")
                    }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == AdminChoices.route,
                    label = { Text(text = stringResource(R.string.create), color = MaterialTheme.colorScheme.onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = { navController.navigate(AdminChoices.route) },
                    icon = {
                        Icon(Icons.Filled.QrCode2, "Create")
                    }
                )
            }
}

@Composable
fun MenuOptions(navController: NavController){

    var showMenu by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(Icons.Filled.Menu ,"Menu", tint = MaterialTheme.colorScheme.onPrimaryContainer)
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.settings), color = MaterialTheme.colorScheme.onPrimaryContainer) },
            onClick = {  navController.navigate(SettingOptions.route) },
            leadingIcon = {
                Icon(Icons.Filled.Settings, "Settings")
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.manual), color = MaterialTheme.colorScheme.onPrimaryContainer) },
            onClick = { navController.navigate(Manual.route) },
            leadingIcon = {
                Icon(Icons.Filled.Book, "Manual")
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.logout), color = MaterialTheme.colorScheme.onPrimaryContainer) },
            onClick = {
                scope.launch {
                    Firebase.auth.signOut()
                    navController.navigate(Login.route)
                }
            },
            leadingIcon = {
                Icon(Icons.Filled.Logout, "Logout")
            }
        )
    }
}

fun emailString (): String {
    val email = Firebase.auth.currentUser?.email!!.split(".")[0].split("@")[0].replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
    return email
}