package ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ui.core.navigation.bottomnavigation.BottomBarItem
import ui.core.navigation.bottomnavigation.NavigationBottomWrapper

@Composable
fun HomeScreen() {
    val items = listOf(BottomBarItem.Training(), BottomBarItem.Stats(), BottomBarItem.Records())
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(items, navController)
        }
    ) {
        Box {
            NavigationBottomWrapper(navController)
        }
    }
}

@Composable
fun BottomNavigation(items: List<BottomBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                icon = it.icon,
                label = { Text(it.title) },
                onClick = {
                    navController.navigate(route = it.route) {
                        //This goes to the stack and check if view is already created. If it does,
                        //Nav won´t open a new view and load the existing one
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        //Prevent to open the same view more than one time
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentDestination?.hierarchy?.any { hierarchy -> hierarchy.route == it.route } == true,
            )
        }
    }
}