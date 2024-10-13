package ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun RecordsActivity(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        initialRoute = "/records"
    ) {
        scene(route = "/records") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Records") }
                    )
                },
                content = { },
                bottomBar = {
                    BottomMenu(
                        onClickTraining = "/training",
                        onClickStats = "/stats",
                        onClickRecords = null,
                        navigator
                    )
                }
            )
        }
        scene(route = "/stats") {
            StatsActivity(navigator)
        }
        scene(route = "/training") {
            TrainingListActivity(navigator)
        }
    }
}