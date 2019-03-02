package com.jrodriguezva.superhero.di.module

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.jrodriguezva.superhero.data.local.AppDatabase
import com.jrodriguezva.superhero.data.local.SuperheroDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "Superhero.db").build()
    }

    @Provides
    @Singleton
    fun provideDao(@NonNull database: AppDatabase): SuperheroDao {
        return database.superheroDao()
    }
}