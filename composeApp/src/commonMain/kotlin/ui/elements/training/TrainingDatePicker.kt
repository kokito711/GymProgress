package ui.elements.training

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import expects.logDebug
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
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
    logDebug("MyCustomDatePicker", "Confirming with date: $selectedDate")

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