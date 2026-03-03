package di

import domain.training.AddExerciseUseCase
import domain.training.DeleteExerciseBySessionUseCase
import domain.training.EditExerciseBySessionUseCase
import domain.training.EndTrainingSessionUseCase
import domain.training.GetAllExercisesBySessionUseCase
import domain.training.GetExerciseByIdUseCase
import domain.training.GetSavedTrainingsUseCase
import domain.training.GetTrainingByIdUseCase
import domain.training.StartNewTrainingSessionUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { StartNewTrainingSessionUseCase(get()) }
    factory { EndTrainingSessionUseCase(get()) }
    factory { GetSavedTrainingsUseCase(get()) }
    factory { GetTrainingByIdUseCase(get()) }

    // Exercise Use Cases
    factory { GetAllExercisesBySessionUseCase(get()) }
    factory { EditExerciseBySessionUseCase(get()) }
    factory { DeleteExerciseBySessionUseCase(get()) }
    factory { AddExerciseUseCase(get()) }
    factory { GetExerciseByIdUseCase(get()) }
}
