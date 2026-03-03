package di

import data.database.dao.ExerciseRepositoryImpl
import data.database.dao.TrainingRepositoryImpl
import db.GymProgressDatabase
import domain.ExerciseRepository
import domain.TrainingRepository
import org.koin.dsl.module

val dataModule = module {
    single<TrainingRepository> { TrainingRepositoryImpl(get()) }
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
    single { GymProgressDatabase(get()) }
}
