package domain.training

import domain.TrainingRepository
import expects.logError
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class StartNewTrainingSessionUseCase(
    private val repository: TrainingRepository
) {
    /**
     * Executes the business logic to start a new training session.
     * @param sessionDateMillis The date selected by the user.
     * @return The ID of the newly created training session, or null on failure.
     */
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(sessionDateMillis: Long): Long? {
        val startTimeMillis = Clock.System.now().toEpochMilliseconds()
        if (sessionDateMillis > startTimeMillis) {
            logError("StartNewTrainingSessionUseCase", "Cannot start a session for a future date.")
            throw RuntimeException()
        }
        return repository.startNewTraining(
            date = sessionDateMillis,
            startTime = startTimeMillis
        )
    }
}