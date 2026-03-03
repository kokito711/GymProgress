package data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.GymProgressDatabase
import db.Exercise as ExerciseDb
import domain.ExerciseRepository
import domain.training.model.training.Exercise
import expects.logDebug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ExerciseRepositoryImpl(private val database: GymProgressDatabase) : ExerciseRepository {

    private val exerciseQueries = database.exerciseQueries

    override fun getExercisesBySession(sessionId: Long): Flow<List<Exercise>> {
        logDebug("ExerciseRepositoryImpl", "getExercisesBySession called - sessionId: $sessionId")
        return exerciseQueries.getAllBySession(sessionId)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { sqlDelightExerciseList ->
                sqlDelightExerciseList.map { exerciseDb ->
                    exerciseDb.toDomainModel()
                }
            }
            .onEach { exercises ->
                logDebug("ExerciseRepositoryImpl", "Retrieved ${exercises.size} exercises for session $sessionId")
            }
    }

    override fun getExerciseById(exerciseId: Long): Flow<Exercise?> {
        logDebug("ExerciseRepositoryImpl", "getExerciseById called - exerciseId: $exerciseId")
        return exerciseQueries.getById(exerciseId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDomainModel() }
    }

    override suspend fun updateExercise(exercise: Exercise) {
        logDebug("ExerciseRepositoryImpl", "updateExercise called - id: ${exercise.id}, name: ${exercise.name}")
        exercise.id?.let { id ->
            exerciseQueries.update(
                name = exercise.name,
                duration = exercise.duration,
                description = exercise.description,
                comments = exercise.comments,
                id = id
            )
        }
    }

    override suspend fun deleteExercise(exerciseId: Long) {
        logDebug("ExerciseRepositoryImpl", "deleteExercise called - id: $exerciseId")
        exerciseQueries.delete(id = exerciseId)
    }

    override suspend fun addExercise(exercise: Exercise) {
        logDebug("ExerciseRepositoryImpl", "addExercise called - session: ${exercise.sessionId}, name: ${exercise.name}")
        exerciseQueries.insert(
            session_id = exercise.sessionId,
            name = exercise.name,
            duration = exercise.duration,
            description = exercise.description,
            comments = exercise.comments
        )
    }

    private fun ExerciseDb.toDomainModel(): Exercise {
        return Exercise(
            id = this.id,
            sessionId = this.session_id,
            name = this.name,
            duration = this.duration,
            description = this.description,
            comments = this.comments
        )
    }
}
