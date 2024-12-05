package data.database.dao

import domain.TrainingRepository
import domain.model.TrainingModel

class TrainingRepositoryImpl : TrainingRepository {
    override suspend fun getTrainingList(): List<TrainingModel> {
        TODO("Not yet implemented")
    }
}