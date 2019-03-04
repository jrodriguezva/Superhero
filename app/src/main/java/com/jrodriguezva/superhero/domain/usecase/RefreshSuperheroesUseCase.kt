package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class RefreshSuperheroesUseCase @Inject constructor(
    private val repository: SuperheroesDataSource,
    private val executor: Executor
) : UseCase(executor) {

    fun execute() = asyncExecute {
        repository.refreshSuperheroes()
    }
}