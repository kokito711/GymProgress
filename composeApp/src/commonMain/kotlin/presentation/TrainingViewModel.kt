package presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.training.EndTrainingSessionUseCase
import domain.training.GetSavedTrainingsUseCase
import domain.training.StartNewTrainingSessionUseCase
import domain.training.model.training.Session
import expects.logDebug
import expects.logError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class TrainingViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val startNewTrainingSessionUseCase: StartNewTrainingSessionUseCase,
    private val endTrainingSessionUseCase: EndTrainingSessionUseCase,
    private val getSavedTrainingsUseCase: GetSavedTrainingsUseCase
) : ViewModel() {
    private val _isSessionStarted =
        MutableStateFlow(savedStateHandle.get<Boolean>(IS_SESSION_STARTED_KEY) ?: false)
    val isSessionStarted: StateFlow<Boolean> = _isSessionStarted.asStateFlow()

    private val _selectedDate = MutableStateFlow(savedStateHandle.get<Long>(SELECTED_DATE_KEY))
    val selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    // --- New State for the current session's ID ---
    private val _currentTrainingId =
        MutableStateFlow(savedStateHandle.get<Long>(CURRENT_TRAINING_ID_KEY))
    val currentTrainingId: StateFlow<Long?> = _currentTrainingId.asStateFlow()

    val savedTrainings: StateFlow<List<Session>> =
        getSavedTrainingsUseCase(pageSize = 20, pageNumber = 1)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    /**
     * Starts a new training session.
     * This function inserts a new record into the database and saves the new session's ID.
     * @param sessionDateMillis The date selected from the DatePicker.
     */
    fun startNewTrainingSession(sessionDateMillis: Long) {
        viewModelScope.launch {
            // Call the repository to insert the new record and get its ID
            val newTrainingId = startNewTrainingSessionUseCase.invoke(sessionDateMillis)

            if (newTrainingId != null) {
                // Update the ViewModel's state
                _currentTrainingId.value = newTrainingId
                savedStateHandle[CURRENT_TRAINING_ID_KEY] = newTrainingId
                setSessionStarted(true) // Mark session as started
                setSelectedDate(sessionDateMillis) // Save the selected date
                logDebug("TrainingViewModel", "New session started with ID: $newTrainingId")
            } else {
                logError("TrainingViewModel", "Failed to start new session.")
                // Handle the error, show a Toast/Snackbar to the user
            }
        }
    }

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

    @OptIn(ExperimentalTime::class)
    fun getFormattedDate(dateMillis: Long?): String {
        logDebug("TrainingViewModel", "getFormattedDate called with: $dateMillis")
        if (dateMillis == null) {
            return "No date selected"
        } else {
            val instant = Instant.fromEpochMilliseconds(dateMillis)
            val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val formattedDate = "${dateTime.day}/${dateTime.month}/${dateTime.year}"
            logDebug("TrainingViewModel", "getFormattedDate returning: $formattedDate")
            return formattedDate
        }
    }

    /**
     * Finishes the current training session.
     * This function updates the end_time in the database and resets the ViewModel's state.
     */
    fun finishCurrentTrainingSession() {
        viewModelScope.launch {
            val trainingId = _currentTrainingId.value
            if (trainingId != null) {
                endTrainingSessionUseCase.invoke(trainingId)
                logDebug("TrainingViewModel", "Session with ID: $trainingId finished.")
                // Reset the state
                _currentTrainingId.value = null
                savedStateHandle[CURRENT_TRAINING_ID_KEY] = null
                setSessionStarted(false)
                setSelectedDate(null)
            }
        }
    }

    companion object {
        const val IS_SESSION_STARTED_KEY = "isSessionStarted"
        const val SELECTED_DATE_KEY = "selectedDate"
        const val CURRENT_TRAINING_ID_KEY = "currentTrainingId"
    }
}