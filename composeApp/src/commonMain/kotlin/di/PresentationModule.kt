package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import presentation.ExerciseDetailViewModel
import presentation.SessionViewModel
import presentation.TrainingViewModel

val presentationModule = module {
    viewModel {
        TrainingViewModel(
            savedStateHandle = get(),
            startNewTrainingSessionUseCase = get(),
            endTrainingSessionUseCase = get(),
            getSavedTrainingsUseCase = get()
        )
    }

    viewModel { (sessionId: Long) ->
        SessionViewModel(
            sessionId = sessionId,
            getAllExercisesBySessionUseCase = get(),
            getTrainingByIdUseCase = get(),
            addExerciseUseCase = get(),
            deleteExerciseUseCase = get()
        )
    }

    viewModel { (exerciseId: Long, sessionId: Long) ->
        ExerciseDetailViewModel(
            exerciseId = exerciseId,
            sessionId = sessionId,
            getExerciseByIdUseCase = get(),
            editExerciseBySessionUseCase = get(),
            addExerciseUseCase = get()
        )
    }
}
