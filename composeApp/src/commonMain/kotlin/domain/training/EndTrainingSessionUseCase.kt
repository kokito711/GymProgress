package domain.training

import domain.TrainingRepository
import expects.logDebug
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Executes the business logic to finish the current training session.
 * @param trainingId The date selected by the user.
 */
class EndTrainingSessionUseCase(
    private val repository: TrainingRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(trainingId: Long) {
        repository.finishTraining(trainingId, endTime = Clock.System.now().toEpochMilliseconds())
        logDebug("EndTrainingSessionUseCase", "Session with ID: $trainingId finished.")
    }
}