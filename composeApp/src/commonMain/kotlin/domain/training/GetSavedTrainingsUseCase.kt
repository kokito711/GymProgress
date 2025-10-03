package domain.training

import domain.TrainingRepository
import domain.training.model.toSession
import domain.training.model.training.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Executes the use case to get a paginated list of saved trainings.
 * Returns a Flow that emits the list of trainings.
 */
class GetSavedTrainingsUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(pageSize: Long, pageNumber: Long): Flow<List<Session>> =
        trainingRepository.getAllTrainings(pageSize, pageNumber)
            .map { trainingModelList ->
                trainingModelList.map { trainingModel ->
                    trainingModel.toSession()
                }
            }
}