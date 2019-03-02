package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.utils.Either
import javax.inject.Inject

class GetSuperheroByIdUseCase @Inject constructor(
    private val repository: SuperheroesDataSource,
    executor: Executor
) : UseCase(executor) {


    fun execute(id: Long, onResult: (Either<GetSuperheroFailure, Superhero>) -> Unit) {

        asyncExecute {
            val superheroResult = repository.getSuperhero(id)
            uiExecute { onResult(superheroResult) }
        }
    }
}