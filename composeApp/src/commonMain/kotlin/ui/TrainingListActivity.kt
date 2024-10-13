package ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TrainingListActivity(
    navigator: Navigator
) {
    NavHost(
        navigator = navigator,
        initialRoute = "/training"
    ) {
        scene(route = "/training") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Entrenamiento") },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                            }
                        }
                    )
                },
                content = { },
                bottomBar = {
                    BottomMenu(
                        onClickTraining = null,
                        onClickStats = "/stats",
                        onClickRecords = "/records",
                        navigator
                    )
                }
            )
        }
        scene(route = "/stats") {
            StatsActivity(navigator)
        }
        scene(route = "/records") {
            RecordsActivity(navigator)
        }
    }

}