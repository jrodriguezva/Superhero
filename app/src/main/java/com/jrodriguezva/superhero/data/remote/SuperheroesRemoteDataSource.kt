package com.jrodriguezva.superhero.data.remote

import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.utils.Either
import javax.inject.Inject

class SuperheroesRemoteDataSource @Inject constructor(val api: WebService) : SuperheroesDataSource {
    override suspend fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>> {
        val response = api.getHeroes()
        if (response.isSuccessful) {
            response.body()
                ?.let { superheroes -> return Either.Right(superheroes.superheroes.map { it.convertToDomain() }) }
                ?: kotlin.run {
                    return Either.Left(GetSuperheroesFailure.SuperheroesNotFound())
                }
        } else {
            return Either.Left(GetSuperheroesFailure.NetworkConnection())
        }
    }
}