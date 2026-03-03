package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.training.AddExerciseUseCase
import domain.training.EditExerciseBySessionUseCase
import domain.training.GetExerciseByIdUseCase
import domain.training.model.training.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseDetailViewModel(
    private val exerciseId: Long,
    private val sessionId: Long,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val editExerciseBySessionUseCase: EditExerciseBySessionUseCase,
    private val addExerciseUseCase: AddExerciseUseCase
) : ViewModel() {

    val exercise: StateFlow<Exercise?> = if (exerciseId != -1L) {
        getExerciseByIdUseCase(exerciseId)
    } else {
        MutableStateFlow(Exercise(sessionId = sessionId, name = "", duration = "00:00:00"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun saveExercise(name: String, duration: String, description: String?, comments: String?) {
        val currentExercise = exercise.value ?: return
        viewModelScope.launch {
            val updatedExercise = currentExercise.copy(
                name = name,
                duration = duration,
                description = description,
                comments = comments
            )
            
            if (exerciseId != -1L) {
                editExerciseBySessionUseCase(updatedExercise)
            } else {
                addExerciseUseCase(updatedExercise)
            }
        }
    }
}
