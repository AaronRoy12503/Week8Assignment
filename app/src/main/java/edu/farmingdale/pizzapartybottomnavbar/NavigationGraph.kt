package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment

// Data class to hold settings
data class AppSettings(
    var darkMode: Boolean = false,
    var notifications: Boolean = true,
    var fontSize: Float = 1.0f
)

// Create a singleton object to maintain settings state
object SettingsManager {
    var currentSettings = mutableStateOf(AppSettings())
}

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit
) {
    var settings by remember { SettingsManager.currentSettings }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Settings", style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dark Mode Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode")
                    Switch(
                        checked = settings.darkMode,
                        onCheckedChange = { settings = settings.copy(darkMode = it) }
                    )
                }

                // Notifications Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notifications")
                    Switch(
                        checked = settings.notifications,
                        onCheckedChange = { settings = settings.copy(notifications = it) }
                    )
                }

                // Font Size Slider
                Text("Font Size")
                Slider(
                    value = settings.fontSize,
                    onValueChange = { settings = settings.copy(fontSize = it) },
                    valueRange = 0.5f..2.0f,
                    steps = 2
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Small")
                    Text("Normal")
                    Text("Large")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}

// Update AppScaffold to include settings dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    showBottomBar: Boolean,
    content: @Composable () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Show settings dialog if showSettings is true
    if (showSettings) {
        SettingsDialog(onDismiss = { showSettings = false })
    }

    val drawerItems = listOf(
        DrawerItem("Home", Icons.Default.Home, BottomNavigationItems.Welcome.route),
        DrawerItem("Pizza Order", Icons.Default.LocalPizza, BottomNavigationItems.PizzaScreen.route),
        DrawerItem("GPA Calculator", Icons.Default.Calculate, BottomNavigationItems.GpaAppScreen.route),
        DrawerItem("Settings", Icons.Default.Settings, BottomNavigationItems.Screen3.route),
        DrawerItem("Help", Icons.Default.Help, "help_route"),
        DrawerItem("About", Icons.Default.Info, "about_route")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
                Divider()
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(item.route)
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pizza Party App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showSettings = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}

data class DrawerItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

// Update your NavigationGraph to use the AppScaffold
@Composable
fun NavigationGraph(
    navController: NavHostController,
    onBottomBarVisibilityChanged: (Boolean) -> Unit
) {
    AppScaffold(
        navController = navController,
        showBottomBar = true
    ) {
        NavHost(navController, startDestination = BottomNavigationItems.Welcome.route) {
            composable(BottomNavigationItems.Welcome.route) {
                onBottomBarVisibilityChanged(false)
                SplashScreen(navController = navController)
            }
            composable(BottomNavigationItems.PizzaScreen.route) {
                onBottomBarVisibilityChanged(true)
                PizzaPartyScreen()
            }
            composable(BottomNavigationItems.GpaAppScreen.route) {
                onBottomBarVisibilityChanged(true)
                GpaAppScreen()
            }
            composable(BottomNavigationItems.Screen3.route) {
                onBottomBarVisibilityChanged(true)
                Screen3()
            }
            // Add new routes for Help and About
            composable("help_route") {
                onBottomBarVisibilityChanged(true)
                HelpScreen()
            }
            composable("about_route") {
                onBottomBarVisibilityChanged(true)
                AboutScreen()
            }
        }
    }
}

// Placeholder screens for new routes
@Composable
fun HelpScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Help & Support",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("This is the help screen where you can add FAQs and support information.")
    }
}

@Composable
fun AboutScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "About",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Pizza Party App v1.0\nDeveloped with Jetpack Compose")
    }
}