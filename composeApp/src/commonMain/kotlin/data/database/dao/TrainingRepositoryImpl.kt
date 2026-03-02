package data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.GymProgressDatabase
import db.Training
import domain.TrainingRepository
import domain.training.model.TrainingModel
import expects.logDebug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class TrainingRepositoryImpl(private val database: GymProgressDatabase) : TrainingRepository {

    private val queries = database.trainingQueries

    override fun startNewTraining(date: Long, startTime: Long): Long? {
        logDebug("TrainingRepositoryImpl", "startNewTraining called - date: $date, startTime: $startTime")
        return queries.transactionWithResult {
            queries.insert(date = date, start_time = startTime)
            val id = queries.selectLastInsertedId().executeAsOneOrNull()
            logDebug("TrainingRepositoryImpl", "New training inserted with ID: $id")
            id
        }
    }

    override fun finishTraining(trainingId: Long, endTime: Long) {
        logDebug("TrainingRepositoryImpl", "finishTraining called - trainingId: $trainingId, endTime: $endTime")
        queries.updateEndTime(end_time = endTime, id = trainingId)
    }

    override fun getAllTrainings(pageSize: Long, pageNumber: Long): Flow<List<TrainingModel>> {
        logDebug("TrainingRepositoryImpl", "getAllTrainings called - pageSize: $pageSize, pageNumber: $pageNumber")
        val offset = (pageNumber - 1) * pageSize
        return queries.getAllTrainingsPaginated(limit = pageSize, offset = offset)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { sqlDelightTrainingList ->
                sqlDelightTrainingList.map { trainingFromDb ->
                    trainingFromDb.toDomainModel()
                }
            }
            .onEach { trainings ->
                logDebug("TrainingRepositoryImpl", "Retrieved ${trainings.size} trainings from DB")
            }
    }

    private fun Training.toDomainModel(): TrainingModel {
        return TrainingModel(
            id = this.id,
            date = this.date,
            startTime = this.start_time,
            endTime = this.end_time
        )
    }
}
