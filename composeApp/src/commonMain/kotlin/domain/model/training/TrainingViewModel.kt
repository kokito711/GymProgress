package domain.model.training

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TrainingViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _isSessionStarted =
        MutableStateFlow(savedStateHandle.get<Boolean>(IS_SESSION_STARTED_KEY) ?: false)
    val isSessionStarted: StateFlow<Boolean> = _isSessionStarted.asStateFlow()

    private val _selectedDate = MutableStateFlow(savedStateHandle.get<Long>(SELECTED_DATE_KEY))
    val selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    fun setSessionStarted(value: Boolean) {
        viewModelScope.launch {
            _isSessionStarted.emit(value)
            savedStateHandle[IS_SESSION_STARTED_KEY] = value
        }
    }

    fun setSelectedDate(date: Long?) {
        viewModelScope.launch {
            _selectedDate.emit(date)
            savedStateHandle[SELECTED_DATE_KEY] = date
        }
    }

    fun getFormattedDate(dateMillis: Long?): String =
        if (dateMillis == null) {
            "No date selected"
        } else {
            val instant = Instant.fromEpochMilliseconds(dateMillis)
            val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dateTime.dayOfMonth}/${dateTime.monthNumber}/${dateTime.year}"
        }

    companion object {
        const val IS_SESSION_STARTED_KEY = "isSessionStarted"
        const val SELECTED_DATE_KEY = "selectedDate"
    }
}