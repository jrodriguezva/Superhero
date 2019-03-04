package com.jrodriguezva.superhero.domain.source

import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.model.Superhero

interface SuperheroesDataSource {

    fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>>

    fun getSuperhero(superheroId: Long): Either<GetSuperheroFailure, Superhero> {
        return Either.Left(GetSuperheroFailure.NotImplementedError)
    }

    fun saveSuperhero(superhero: Superhero) {}

    fun refreshSuperheroes() {}

    fun deleteAllSuperheroes() {}
}