package domain.training

import domain.ExerciseRepository
import domain.training.model.training.Exercise
import expects.logDebug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetAllExercisesBySessionUseCase(private val repository: ExerciseRepository) {
    operator fun invoke(sessionId: Long): Flow<List<Exercise>> {
        logDebug("GetAllExercisesBySessionUseCase", "Invoked for sessionId: $sessionId")
        return repository.getExercisesBySession(sessionId).onEach { exercises ->
            logDebug("GetAllExercisesBySessionUseCase", "Retrieved ${exercises.size} exercises for session: $sessionId")
        }
    }
}
