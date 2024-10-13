package ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun StatsActivity(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        initialRoute = "/stats"
    ) {
        scene(route = "/stats") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Estadísticas") }
                    )
                },
                content = { },
                bottomBar = {
                    BottomMenu(
                        onClickTraining = "/training",
                        onClickStats = null,
                        onClickRecords = "/records",
                        navigator
                    )
                }
            )
        }
        scene(route = "/training") {
            TrainingListActivity(navigator)
        }
        scene(route = "/records") {
            RecordsActivity(navigator)
        }
    }

}