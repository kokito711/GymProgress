package ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.core.navigation.Routes
import ui.tabs.records.RecordsScreen
import ui.tabs.stats.StatsScreen
import ui.tabs.training.AddActivity
import ui.tabs.training.Session
import ui.tabs.training.TrainingScreen

@Composable
fun NavigationBottomWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Training.route) {
        composable(Routes.Training.route) {
            TrainingScreen(navController)
        }
        composable(Routes.Records.route) {
            RecordsScreen()
        }
        composable(Routes.Stats.route) {
            StatsScreen()
        }
        composable(Routes.AddActivity.route) {
            AddActivity()
        }
        composable(
            route = "${Routes.StartSession.route}?date={date}",
            arguments = listOf(navArgument("date") { type = NavType.LongType; defaultValue = -1L })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getLong("date")
            Session(navController, date)
        }
    }
}