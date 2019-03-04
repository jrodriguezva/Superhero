package com.jrodriguezva.superhero.di.module

import android.app.Application
import android.content.Context
import com.jrodriguezva.superhero.data.local.SuperheroesLocalDataSource
import com.jrodriguezva.superhero.data.remote.SuperheroesRemoteDataSource
import com.jrodriguezva.superhero.data.repository.SuperheroRepository
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.domain.usecase.Executor
import com.jrodriguezva.superhero.ui.executor.Coroutine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideSuperheroesDataSource(
        superheroRemoteDataSource: SuperheroesRemoteDataSource,
        superheroLocalDataSource: SuperheroesLocalDataSource
    ): SuperheroesDataSource {
        return SuperheroRepository(superheroRemoteDataSource, superheroLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideExecutor(
    ): Executor {
        return Coroutine()
    }
}