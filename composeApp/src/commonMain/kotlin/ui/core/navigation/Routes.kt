package ui.core.navigation

sealed class Routes(val route: String) {

    data object Home : Routes("home")

    //Bottom Menu
    data object Training : Routes("training")
    data object Stats : Routes("stats")
    data object Records : Routes("records")

    //Training navigation
    data object AddActivity : Routes("addActivity")
    data object FinishSession : Routes("finishActivity")
    data object StartSession : Routes("startSession")
}