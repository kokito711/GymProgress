package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import db.GymProgressDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<SqlDriver> {
            NativeSqliteDriver(GymProgressDatabase.Schema, "gymprogress.db")
        }
    }
}