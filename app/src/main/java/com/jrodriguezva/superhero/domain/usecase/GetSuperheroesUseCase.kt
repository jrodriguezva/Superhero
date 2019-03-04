package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class GetSuperheroesUseCase @Inject constructor(
    private val repository: SuperheroesDataSource,
    private val executor: Executor
) : UseCase(executor) {

    fun execute(onResult: (Either<GetSuperheroesFailure, List<Superhero>>) -> Unit) = asyncExecute {
        val superheroesResult = repository.getSuperheroes()
        uiExecute { onResult(superheroesResult) }
    }
}