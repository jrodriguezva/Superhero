package com.jrodriguezva.superhero.domain.source

import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.utils.Either

interface SuperheroesDataSource {

    suspend fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>>

    suspend fun getSuperhero(superheroId: Long): Either<GetSuperheroFailure, Superhero> {
        return Either.Left(GetSuperheroFailure.NotImplementedError)
    }

    suspend fun saveSuperhero(superhero: Superhero) {}

    suspend fun refreshSuperheroes() {}

    suspend fun deleteAllSuperheroes() {}
}