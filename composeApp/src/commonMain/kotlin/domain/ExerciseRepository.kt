package domain

import domain.training.model.training.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercisesBySession(sessionId: Long): Flow<List<Exercise>>
    fun getExerciseById(exerciseId: Long): Flow<Exercise?>
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(exerciseId: Long)
    suspend fun addExercise(exercise: Exercise)
}
