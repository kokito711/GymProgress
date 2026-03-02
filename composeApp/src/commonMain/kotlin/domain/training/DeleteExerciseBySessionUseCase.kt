package domain.training

import domain.ExerciseRepository
import expects.logDebug

class DeleteExerciseBySessionUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exerciseId: Long) {
        logDebug("DeleteExerciseBySessionUseCase", "Invoked for exerciseId: $exerciseId")
        repository.deleteExercise(exerciseId)
    }
}
