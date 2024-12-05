package ui.home.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import ui.core.navigation.activitynavigation.ActivityBarItem
import ui.core.navigation.activitynavigation.ActivityWrapper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen() {
    val navController = rememberNavController()

    val items = listOf(
        ActivityBarItem.StartSession(),
        ActivityBarItem.FinishSession(),
        ActivityBarItem.AddActivity()
    )
    Box(Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.background(Color.Red),
            /*navigationIcon = {
                IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
            }
        },*/
            title = { Text(text = "") },
            actions = {
                for (item in items) {
                    IconButton(
                        onClick = {
                            navController.navigate(route = item.route) {
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
                        }) {
                    }
                    item.icon
                }
            }
        )
        Box { ActivityWrapper(navController) }
    }
}