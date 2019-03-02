package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import javax.inject.Inject


class RefreshSuperheroesUseCase @Inject constructor(
    private val repository: SuperheroesDataSource,
    private val executor: Executor
) : UseCase(executor) {

    fun execute() {
        asyncExecute {
            repository.refreshSuperheroes()
        }
    }
}