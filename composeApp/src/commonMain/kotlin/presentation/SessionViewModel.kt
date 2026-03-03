package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.training.AddExerciseUseCase
import domain.training.DeleteExerciseBySessionUseCase
import domain.training.GetAllExercisesBySessionUseCase
import domain.training.GetTrainingByIdUseCase
import domain.training.model.TrainingModel
import domain.training.model.training.Exercise
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SessionViewModel(
    private val sessionId: Long,
    private val getAllExercisesBySessionUseCase: GetAllExercisesBySessionUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseBySessionUseCase
) : ViewModel() {

    val exercises: StateFlow<List<Exercise>> = getAllExercisesBySessionUseCase(sessionId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val sessionDetails: StateFlow<TrainingModel?> = getTrainingByIdUseCase(sessionId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun addExercise(name: String, duration: String, description: String?, comments: String?) {
        viewModelScope.launch {
            addExerciseUseCase(
                Exercise(
                    sessionId = sessionId,
                    name = name,
                    duration = duration,
                    description = description,
                    comments = comments
                )
            )
        }
    }

    fun deleteExercise(exerciseId: Long) {
        viewModelScope.launch {
            deleteExerciseUseCase(exerciseId)
        }
    }
}
