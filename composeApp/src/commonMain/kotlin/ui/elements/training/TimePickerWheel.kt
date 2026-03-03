package ui.elements.training

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerWheel(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NumberPicker(value = hours, range = 0..23, onValueChange = onHoursChange, label = "HH")
        Text(":", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(horizontal = 4.dp))
        NumberPicker(value = minutes, range = 0..59, onValueChange = onMinutesChange, label = "MM")
        Text(":", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(horizontal = 4.dp))
        NumberPicker(value = seconds, range = 0..59, onValueChange = onSecondsChange, label = "SS")
    }
}

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    label: String
) {
    androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        // Simplificación: Un TextField numérico o botones +/- para KMP básico
        // En una app real usaríamos una WheelView personalizada, pero aquí usaremos algo funcional
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.IconButton(onClick = { if (value > range.first) onValueChange(value - 1) }) {
                Text("-")
            }
            Text(
                text = value.toString().padStart(2, '0'),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.width(30.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            androidx.compose.material3.IconButton(onClick = { if (value < range.last) onValueChange(value + 1) }) {
                Text("+")
            }
        }
    }
}
