import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.core.navigation.NavigationWrapper


@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationWrapper()
    }
}