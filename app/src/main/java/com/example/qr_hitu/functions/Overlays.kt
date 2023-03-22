package com.example.qr_hitu.functions

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.text.style.IconMarginSpan
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.adminScreens.create.ViewModel1
import com.example.qr_hitu.screens.components.*
import com.example.qr_hitu.screens.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLayouts(navController: NavController, viewModel : ViewModel1){

    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val destinationRoute = currentDestination?.route ?: Login.route

    Scaffold(
        topBar = {

            when {
                destinationRoute.contains(MalfList.route) || destinationRoute.contains(ScanAdmin.route) -> TopBar1(navController = navController)
                destinationRoute.contains(Create1.route) || destinationRoute.contains(Create2.route) -> TopBar2(navController = navController)
                destinationRoute.contains(Create3.route) -> TopBar3(navController = navController)
                destinationRoute.contains(ScanProf.route) || destinationRoute.contains(ScanInput.route) -> TopBarUser(navController = navController)
                destinationRoute.contains(ScanAdminInfo.route) -> Topbar4(navController = navController)
            }

        },
        bottomBar = {

            if(destinationRoute.contains(MalfList.route) || destinationRoute.contains(ScanAdmin.route)) {
                BottomBar(navController = navController)
            }

        },
        scaffoldState = scaffoldState,
    ) { innerPadding ->
            QrHituNavHost(
                navController = navController,
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )

    }

}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar1(navController: NavController){

    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

            TopAppBar(
                title = { Text(text = "Admin", color = md_theme_light_onPrimaryContainer) },
                colors = TopAppBarDefaults.smallTopAppBarColors(md_theme_light_primaryContainer),
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Filled.Menu, "Menu", tint = md_theme_light_onPrimaryContainer)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Definições") },
                            onClick = {  navController.navigate(DefOptions.route) },
                            leadingIcon = {
                                Icon(Icons.Filled.Settings, null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Manual") },
                            onClick = { navController.navigate(Manual.route) },
                            leadingIcon = {
                                Icon(Icons.Filled.Book, null)
                            }
                        )
                    }
                }
            )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar2(navController: NavController){

    TopAppBar(
        title = { Text(text = "Admin", color = md_theme_light_onPrimaryContainer) },
        colors = TopAppBarDefaults.smallTopAppBarColors(md_theme_light_primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = md_theme_light_onPrimaryContainer)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar3(navController: NavController){

    TopAppBar(
        title = { Text(text = "Admin", color = md_theme_light_onPrimaryContainer) },
        colors = TopAppBarDefaults.smallTopAppBarColors(md_theme_light_primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.navigate(MalfList.route) }) {
                Icon(Icons.Filled.Close ,"Close", tint = md_theme_light_onPrimaryContainer)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar4(navController: NavController){

    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(text = "Admin", color = md_theme_light_onPrimaryContainer) },
        colors = TopAppBarDefaults.smallTopAppBarColors(md_theme_light_primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack ,"Back", tint = md_theme_light_onPrimaryContainer)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Edit, "Edit", tint = md_theme_light_onPrimaryContainer)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, "Delete", tint = md_theme_light_onPrimaryContainer)
            }
            IconButton(onClick = {  showMenu = !showMenu }) {
                Icon(Icons.Filled.Menu, "Menu", tint = md_theme_light_onPrimaryContainer)
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Definições") },
                    onClick = {  navController.navigate(DefOptions.route) },
                    leadingIcon = {
                        Icon(Icons.Filled.Settings, null)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Manual") },
                    onClick = { navController.navigate(Manual.route) },
                    leadingIcon = {
                        Icon(Icons.Filled.Book, null)
                    }
                )
            }
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser(navController: NavController){

    TopAppBar(
        title = { Text(text = "*Nome do Professor*", color = md_theme_light_onPrimaryContainer) },
        colors = TopAppBarDefaults.smallTopAppBarColors(md_theme_light_primaryContainer),
        navigationIcon = {
            IconButton(onClick = { navController.navigate(MalfList.route) }) {
                Icon(Icons.Filled.Menu ,"Menu", tint = md_theme_light_onPrimaryContainer)
            }
        }
    )

}


@Composable
fun BottomBar(navController: NavController){

            BottomAppBar(
                containerColor = md_theme_light_primaryContainer
            ) {
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == MalfList.route,
                    label = { Text(text = "Avarias", color = md_theme_light_onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= md_theme_light_onSecondaryContainer
                    ),
                    onClick = { navController.navigate(MalfList.route) },
                    icon = {
                        Icon(Icons.Filled.FormatListBulleted, "Avarias")
                    }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == ScanAdmin.route,
                    label = { Text(text = "Scanner", color = md_theme_light_onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= md_theme_light_onSecondaryContainer
                    ),
                    onClick = { navController.navigate(ScanAdmin.route) },
                    icon = {
                        Icon(Icons.Filled.QrCodeScanner, "Scanner")
                    }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == Create1.route,
                    label = { Text(text = "Create", color = md_theme_light_onPrimaryContainer) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor= md_theme_light_onSecondaryContainer
                    ),
                    onClick = { navController.navigate(Create1.route) },
                    icon = {
                        Icon(Icons.Filled.QrCode2, "Create")
                    }
                )
            }
}