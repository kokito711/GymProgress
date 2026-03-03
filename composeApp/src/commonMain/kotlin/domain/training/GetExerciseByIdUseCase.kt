package domain.training

import domain.ExerciseRepository
import domain.training.model.training.Exercise
import kotlinx.coroutines.flow.Flow

class GetExerciseByIdUseCase(private val repository: ExerciseRepository) {
    operator fun invoke(exerciseId: Long): Flow<Exercise?> {
        return repository.getExerciseById(exerciseId)
    }
}
