package domain.training

import domain.TrainingRepository
import domain.training.model.TrainingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTrainingByIdUseCase(private val repository: TrainingRepository) {
    operator fun invoke(trainingId: Long): Flow<TrainingModel?> {
        // Asumimos que añadiremos getTrainingById al repositorio
        return repository.getTrainingById(trainingId)
    }
}
