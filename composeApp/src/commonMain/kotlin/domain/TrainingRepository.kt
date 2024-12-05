package domain

import domain.model.TrainingModel

interface TrainingRepository {
    suspend fun getTrainingList(): List<TrainingModel>
}