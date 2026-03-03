package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import db.GymProgressDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<SqlDriver> {
            AndroidSqliteDriver(GymProgressDatabase.Schema, get(), "gymprogress.db")
        }
    }
}