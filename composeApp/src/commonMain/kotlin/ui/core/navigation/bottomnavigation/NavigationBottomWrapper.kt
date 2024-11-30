package ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.core.navigation.Routes
import ui.home.tabs.records.RecordsScreen
import ui.home.tabs.stats.StatsScreen
import ui.home.tabs.training.TrainingScreen

@Composable
fun NavigationBottomWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Training.route) {
        composable(Routes.Training.route) {
            TrainingScreen()
        }
        composable(Routes.Records.route) {
            RecordsScreen()
        }
        composable(Routes.Stats.route) {
            StatsScreen()
        }
    }
}