package io.github.droidkaigi.confsched2018.test.di

import android.app.Application
import androidx.room.Room
import io.github.droidkaigi.confsched2018.data.db.AppDatabase
import io.github.droidkaigi.confsched2018.di.DatabaseModule

class StubDatabaseModule : DatabaseModule() {

    override fun provideDb(app: Application): AppDatabase =
        Room
            .inMemoryDatabaseBuilder(app, AppDatabase::class.java)
            .fallbackToDestructiveMigration().build()

}
