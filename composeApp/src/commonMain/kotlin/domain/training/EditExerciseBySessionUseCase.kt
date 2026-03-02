package domain.training

import domain.ExerciseRepository
import domain.training.model.training.Exercise
import expects.logDebug

class EditExerciseBySessionUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exercise: Exercise) {
        logDebug("EditExerciseBySessionUseCase", "Invoked for exerciseId: ${exercise.id}")
        repository.updateExercise(exercise)
    }
}
