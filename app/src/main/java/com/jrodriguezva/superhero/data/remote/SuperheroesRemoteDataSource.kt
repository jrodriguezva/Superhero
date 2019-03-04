package com.jrodriguezva.superhero.data.remote

import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.testing.OpenForTesting
import com.jrodriguezva.superhero.utils.NetworkHandler
import javax.inject.Inject

@OpenForTesting
class SuperheroesRemoteDataSource @Inject constructor(
    val api: WebService,
    private val networkHandler: NetworkHandler
) : SuperheroesDataSource {
    override fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>> {
        return when {
            networkHandler.isConnected -> {
                val execute = api.getHeroes().execute()
                val response = ApiResponse.create(execute)
                when (response) {
                    is ApiResponse.ApiEmptyResponse -> Either.Left(GetSuperheroesFailure.SuperheroesNotFound)
                    is ApiResponse.ApiSuccessResponse -> {
                        val map = response.body.superheroes.map { it.convertToDomain() }
                        Either.Right(map)
                    }
                    is ApiResponse.ApiErrorResponse -> Either.Left(GetSuperheroesFailure.NetworkConnection)
                }
            }
            else -> Either.Left(GetSuperheroesFailure.NetworkConnection)
        }
    }
}