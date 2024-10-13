import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.BottomMenu
import ui.RecordsActivity
import ui.StatsActivity
import ui.TrainingListActivity


@Composable
@Preview
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = "/home"
        ) {
            scene(route = "/home") {
                Scaffold(
                    bottomBar = {
                        BottomMenu(
                            onClickTraining = "/training",
                            onClickStats = "/stats",
                            onClickRecords = "/records",
                            navigator
                        )
                    }
                ) { innerPadding ->
                    // Yourscreen content goes here
                    Text(
                        text = "This is the main content area",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            scene(route = "/training") {
                TrainingListActivity(navigator)
            }
            scene(route = "/stats") {
                StatsActivity(navigator)
            }
            scene(route = "/records") {
                RecordsActivity(navigator)
            }
        }
    }
}