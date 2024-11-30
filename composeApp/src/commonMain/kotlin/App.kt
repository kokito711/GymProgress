import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.core.navigation.NavigationWrapper


@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationWrapper()
        /* Scaffold(
             bottomBar = {
                 BottomMenu(
                     onClickTraining = "/training",
                     onClickStats = "/stats",
                     onClickRecords = "/records"
                 )
             }
         ) { innerPadding ->
             // Yourscreen content goes here
             Text(
                 text = "This is the main content area",
                 modifier = Modifier.padding(innerPadding)
             )
         }*/
    }
    /*PreComposeApp {
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
    }*/
}