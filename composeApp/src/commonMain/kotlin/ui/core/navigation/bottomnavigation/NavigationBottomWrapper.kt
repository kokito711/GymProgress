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
import ui.tabs.training.AddExerciseScreen
import ui.tabs.training.ExerciseDetailScreen
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
            val date = backStackEntry.arguments?.read {
                try {
                    getLong("date")
                } catch (e: Exception) {
                    getString("date").toLongOrNull() ?: -1L
                }
            } ?: -1L
            Session(navController, date)
        }
        composable(
            route = "${Routes.ExerciseDetail.route}/{exerciseId}",
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.read {
                try {
                    getLong("exerciseId")
                } catch (e: Exception) {
                    getString("exerciseId")?.toLongOrNull() ?: -1L
                }
            } ?: -1L
            ExerciseDetailScreen(navController, exerciseId)
        }
        composable(
            route = "${Routes.AddExercise.route}/{sessionId}",
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.read {
                try {
                    getLong("sessionId")
                } catch (e: Exception) {
                    getString("sessionId")?.toLongOrNull() ?: -1L
                }
            } ?: -1L
            AddExerciseScreen(navController, sessionId)
        }
    }
}
