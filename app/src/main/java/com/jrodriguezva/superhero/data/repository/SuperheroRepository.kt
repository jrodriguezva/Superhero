package com.jrodriguezva.superhero.data.repository

import com.jrodriguezva.superhero.data.local.SuperheroesLocalDataSource
import com.jrodriguezva.superhero.data.remote.SuperheroesRemoteDataSource
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.funtional.fold
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroFailure
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.source.SuperheroesDataSource
import com.jrodriguezva.superhero.testing.OpenForTesting
import com.jrodriguezva.superhero.utils.EspressoIdlingResource
import javax.inject.Singleton

@OpenForTesting
@Singleton
class SuperheroRepository(
    val superheroRemoteDataSource: SuperheroesRemoteDataSource,
    val superheroLocalDataSource: SuperheroesLocalDataSource
) : SuperheroesDataSource {


    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedSuperheroes: LinkedHashMap<Long, Superhero> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private var cacheIsDirty = false

    override fun getSuperheroes(): Either<GetSuperheroesFailure, List<Superhero>> {
        // Respond immediately with cache if available and not dirty
        if (cachedSuperheroes.isNotEmpty() && !cacheIsDirty) {
            return Either.Right(ArrayList(cachedSuperheroes.values))
        }

        EspressoIdlingResource.increment() // Set app as busy.

        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            return getSuperheroesFromRemoteDataSource()
        } else {
            return superheroLocalDataSource.getSuperheroes().fold(
                {
                    return@fold getSuperheroesFromRemoteDataSource()
                }, {
                    refreshCache(it)
                    EspressoIdlingResource.decrement() // Set app as idle.
                    return@fold Either.Right(ArrayList(cachedSuperheroes.values))
                }
            )
        }
    }

    override fun saveSuperhero(superhero: Superhero) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(superhero) {
            superheroLocalDataSource.saveSuperhero(it)
        }
    }

    override fun getSuperhero(superheroId: Long): Either<GetSuperheroFailure, Superhero> {
        val superheroInCache = getSuperheroWithId(superheroId)

        // Respond immediately with cache if available
        if (superheroInCache != null) {
            return Either.Right(superheroInCache)
        }

        EspressoIdlingResource.increment() // Set app as busy.

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        return superheroLocalDataSource.getSuperhero(superheroId).fold({
            EspressoIdlingResource.decrement() // Set app as idle.
            return@fold Either.Left(it)
        }, {
            cacheAndPerform(it) {
                EspressoIdlingResource.decrement() // Set app as idle.
            }
            return@fold Either.Right(it)
        })

    }

    override fun deleteAllSuperheroes() {
        superheroLocalDataSource.deleteAllSuperheroes()
        cachedSuperheroes.clear()
    }

    override fun refreshSuperheroes() {
        cacheIsDirty = true
    }


    private fun getSuperheroesFromRemoteDataSource(): Either<GetSuperheroesFailure, List<Superhero>> {
        return superheroRemoteDataSource.getSuperheroes().fold({
            EspressoIdlingResource.decrement() // Set app as idle.
            Either.Left(it)
        }, { superheroes ->
            refreshLocalDataSource(superheroes)
            superheroLocalDataSource.getSuperheroes().fold(
                {
                    EspressoIdlingResource.decrement() // Set app as idle.
                    Either.Left(it)
                }, {
                    refreshCache(it)
                    EspressoIdlingResource.decrement() // Set app as idle.
                    Either.Right(ArrayList(cachedSuperheroes.values))
                }
            )
        })
    }

    private fun refreshCache(superheroes: List<Superhero>) {
        cachedSuperheroes.clear()
        superheroes.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(superheroes: List<Superhero>) {
        superheroLocalDataSource.deleteAllSuperheroes()
        for (superhero in superheroes) {
            superheroLocalDataSource.saveSuperhero(superhero)
        }
    }

    private fun getSuperheroWithId(id: Long) = cachedSuperheroes[id]

    private inline fun cacheAndPerform(superhero: Superhero, perform: (Superhero) -> Unit) {
        superhero.id?.let { cachedSuperheroes.put(it, superhero) }
        perform(superhero)
    }
}