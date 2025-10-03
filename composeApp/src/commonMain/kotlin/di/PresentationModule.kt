package di

import org.koin.dsl.module
import presentation.TrainingViewModel

val presentationModule = module {
    factory {
        TrainingViewModel(
            savedStateHandle = get(),
            startNewTrainingSessionUseCase = get(),
            endTrainingSessionUseCase = get(),
            getSavedTrainingsUseCase = get()
        )
    }
}