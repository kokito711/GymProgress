package ui.core.navigation.activitynavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.core.navigation.Routes
import ui.home.tabs.training.AddActivity
import ui.home.tabs.training.StartSession

@Composable
fun ActivityWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.AddActivity.route) {
        composable(Routes.AddActivity.route) {
            AddActivity()
        }
        composable(Routes.StartSession.route) {
            StartSession()
        }
        //composable(Routes.FinishSession.route) { navController.navigateUp() }
    }
}