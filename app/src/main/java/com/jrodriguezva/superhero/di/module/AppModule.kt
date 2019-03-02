package com.jrodriguezva.superhero.di.module

import com.jrodriguezva.superhero.data.local.SuperheroesLocalDataSource
import com.jrodriguezva.superhero.data.remote.SuperheroesRemoteDataSource
import com.jrodriguezva.superhero.data.repository.SuperheroRepository
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.domain.usecase.Executor
import com.jrodriguezva.superhero.ui.executor.Coroutines
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
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
        return Coroutines()
    }
}