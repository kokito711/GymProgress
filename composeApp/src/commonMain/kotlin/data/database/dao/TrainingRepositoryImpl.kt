package data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.GymProgressDatabase
import db.Training
import domain.TrainingRepository
import domain.training.model.TrainingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingRepositoryImpl(private val database: GymProgressDatabase) : TrainingRepository {

    private val queries = database.trainingQueries

    /**
     * Inserts a new training record and returns its ID.
     * It uses a transaction to ensure that the insert and the ID retrieval are atomic.
     */
    override fun startNewTraining(date: Long, startTime: Long): Long? {
        return queries.transactionWithResult {
            queries.insert(date = date, start_time = startTime)
            queries.selectLastInsertedId().executeAsOneOrNull()
        }
    }

    /**
     * Updates the end_time for a given training record.
     */
    override fun finishTraining(trainingId: Long, endTime: Long) {
        queries.updateEndTime(end_time = endTime, id = trainingId)
    }

    /**
     * Retrieves a paginated list of trainings.
     */
    override fun getAllTrainings(pageSize: Long, pageNumber: Long): Flow<List<TrainingModel>> {
        val offset = (pageNumber - 1) * pageSize
        return queries.getAllTrainingsPaginated(limit = pageSize, offset = offset)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { sqlDelightTrainingList -> // 'map' transforms the list
                // For each 'Training' object in the list, call our mapper
                sqlDelightTrainingList.map { trainingFromDb ->
                    trainingFromDb.toDomainModel()
                }
            }
    }

    /**
     * Maps a SQLDelight-generated 'Training' object to a domain 'TrainingModel' object.
     */
    private fun Training.toDomainModel(): TrainingModel {
        return TrainingModel(
            id = this.id,
            date = this.date,
            startTime = this.start_time, // Note the underscore from the DB column name
            endTime = this.end_time
        )
    }
}