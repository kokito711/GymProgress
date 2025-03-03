package ui.core.navigation.trainingnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import ui.core.navigation.Routes

sealed class TrainingMenuItem {
    abstract val route: String
    abstract val title: String
    abstract val icon: @Composable () -> Unit

    data class AddActivity(
        override val route: String = Routes.AddActivity.route,
        override val title: String = "Add activity",
        override val icon: @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ""
            )
        }
    ) : TrainingMenuItem()

    data class FinishSession(
        override val route: String = Routes.FinishSession.route,
        override val title: String = "Finish session",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
        }
    ) : TrainingMenuItem()

    data class StartSession(
        override val route: String = Routes.StartSession.route,
        override val title: String = "Start session",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        }
    ) : TrainingMenuItem()
}