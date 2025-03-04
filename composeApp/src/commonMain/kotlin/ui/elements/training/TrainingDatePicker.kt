package ui.elements.training

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDatePicker(onDateSelected: (Long) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Get the current date
                val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                // Convert the selected date to LocalDate
                val selectedDate = Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                // Return true if the selected date is today or in the past
                return selectedDate <= today
            }
        }
    )
    val selectedDate = datePickerState.selectedDateMillis

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = { selectedDate?.let { onDateSelected(it) } },
                enabled = selectedDate != null
            ) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = { onDismiss() }) { Text("Cancel") } }
    ) { DatePicker(state = datePickerState) }
}