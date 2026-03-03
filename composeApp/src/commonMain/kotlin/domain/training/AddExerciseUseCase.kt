package domain.training

import domain.ExerciseRepository
import domain.training.model.training.Exercise

class AddExerciseUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exercise: Exercise) {
        repository.addExercise(exercise)
    }
}
