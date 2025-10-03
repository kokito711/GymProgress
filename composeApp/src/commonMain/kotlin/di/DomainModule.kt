package di

import domain.training.EndTrainingSessionUseCase
import domain.training.GetSavedTrainingsUseCase
import domain.training.StartNewTrainingSessionUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { StartNewTrainingSessionUseCase(get()) }
    factory { EndTrainingSessionUseCase(get()) }
    factory { GetSavedTrainingsUseCase(get()) }
}