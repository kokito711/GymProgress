package ui.core.navigation.activitynavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import ui.core.navigation.Routes

sealed class ActivityBarItem {
    abstract val route: String
    abstract val title: String
    abstract var isActive: Boolean
    abstract val icon: @Composable () -> Unit

    data class AddActivity(
        override val route: String = Routes.AddActivity.route,
        override val title: String = "Add activity",
        override var isActive: Boolean = false,
        override val icon: @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ""
            )
        }
    ) : ActivityBarItem()

    data class FinishSession(
        override val route: String = Routes.FinishSession.route,
        override val title: String = "Finish session",
        override var isActive: Boolean = false,
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
        }
    ) : ActivityBarItem()

    data class StartSession(
        override val route: String = Routes.StartSession.route,
        override val title: String = "Start session",
        override var isActive: Boolean = true,
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        }
    ) : ActivityBarItem()
}