package com.jrodriguezva.superhero.data.local

import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroFailure
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.utils.Either
import javax.inject.Inject

class SuperheroesLocalDataSource @Inject constructor(val superheroDao: SuperheroDao) : SuperheroesDataSource {
    override suspend fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>> =
        superheroDao.getSuperheroes()?.let { superheroesModel ->
            return when {
                superheroesModel.isEmpty() -> Either.Left(GetSuperheroesFailure.SuperheroesNotFound)
                else -> Either.Right(superheroesModel.map { it.convertToDomain() })
            }
        }
            ?: kotlin.run {
                return Either.Left(GetSuperheroesFailure.SuperheroesNotFound)
            }


    override suspend fun getSuperhero(superheroId: Long): Either<GetSuperheroFailure, Superhero> {
        superheroDao.getSuperhero(superheroId)?.let { superheroModel -> return Either.Right(superheroModel.convertToDomain()) }
            ?: kotlin.run { return Either.Left(GetSuperheroFailure.SuperheroNotFound) }
    }


    override suspend fun saveSuperhero(superhero: Superhero) {
        superheroDao.insert(superhero.convertFromDomain())
    }


    override suspend fun deleteAllSuperheroes() {
        superheroDao.clear()
    }

}