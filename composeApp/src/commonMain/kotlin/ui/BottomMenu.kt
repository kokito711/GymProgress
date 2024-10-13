package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun BottomMenu(
    onClickTraining: String?,
    onClickStats: String?,
    onClickRecords: String?,
    navigator: Navigator
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (onClickTraining != null) {
            Button(onClick = { navigator.navigate(onClickTraining) }) {
                Text("Entrenar")
            }
        }
        if (onClickStats != null) {
            Button(onClick = { navigator.navigate(onClickStats) }) {
                Text("Estadísticas")
            }
        }
        if (onClickRecords != null) {
            Button(onClick = { navigator.navigate(onClickRecords) }) {
                Text("Records")
            }
        }
    }
}