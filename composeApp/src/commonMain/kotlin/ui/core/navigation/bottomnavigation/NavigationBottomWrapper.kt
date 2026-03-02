package ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.read
import expects.logDebug
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
            // Use the savedstate 'read' extension which provides a cross-platform way to access arguments.
            // In KMP, we often use getString and parse, or check for specific Long accessors.
            val date = backStackEntry.arguments?.read {
                // If getLong is not available in commonMain, we fallback to string/long retrieval
                // Depending on the version, getLong might be platform-specific.
                try {
                    getLong("date")
                } catch (e: Exception) {
                    getString("date").toLongOrNull() ?: -1L
                }
            } ?: -1L

            logDebug("NavWrapper", "Date argument from NavHost: $date")
            Session(navController, date)
        }
    }
}