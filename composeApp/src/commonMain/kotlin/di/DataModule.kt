package di

import data.database.dao.TrainingRepositoryImpl
import db.GymProgressDatabase
import domain.TrainingRepository
import org.koin.dsl.module

val dataModule = module {
    single<TrainingRepository> { TrainingRepositoryImpl(get()) }
    single { GymProgressDatabase(get()) }
}