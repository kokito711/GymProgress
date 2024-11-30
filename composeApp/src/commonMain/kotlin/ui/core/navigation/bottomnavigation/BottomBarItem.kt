package ui.core.navigation.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import ui.core.navigation.Routes

sealed class BottomBarItem {
    abstract val route: String
    abstract val title: String
    abstract val icon: @Composable () -> Unit

    data class Training(
        override val route: String = Routes.Training.route,
        override val title: String = "Training",
        override val icon: @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = ""
            )
        }
    ) : BottomBarItem()

    data class Stats(
        override val route: String = Routes.Stats.route,
        override val title: String = "Stats",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Home, contentDescription = "")
        }
    ) : BottomBarItem()

    data class Records(
        override val route: String = Routes.Records.route,
        override val title: String = "Records",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Home, contentDescription = "")
        }
    ) : BottomBarItem()
}