package domain

import domain.training.model.TrainingModel
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun startNewTraining(date: Long, startTime: Long): Long?
    fun finishTraining(trainingId: Long, endTime: Long)
    fun getAllTrainings(pageSize: Long, pageNumber: Long): Flow<List<TrainingModel>>
    fun getTrainingById(id: Long): Flow<TrainingModel?>
}
