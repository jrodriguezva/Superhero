package com.jrodriguezva.superhero.data.repository

import com.jrodriguezva.superhero.data.local.SuperheroesLocalDataSource
import com.jrodriguezva.superhero.data.remote.SuperheroesRemoteDataSource
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.funtional.fold
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not be empty`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SuperheroRepositoryTest {

    private lateinit var superheroRepository: SuperheroRepository
    @Mock
    private lateinit var superheroesRemoteDataSource: SuperheroesRemoteDataSource

    @Mock
    private lateinit var superheroesLocalDataSource: SuperheroesLocalDataSource
    private lateinit var superheroDetails: Superhero
    private lateinit var superheroes: List<Superhero>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        superheroRepository = SuperheroRepository(superheroesRemoteDataSource, superheroesLocalDataSource)
        superheroDetails = Superhero(
            1,
            "Iron Man",
            "photo",
            "Anthony Edward 'Tony' Stark",
            "1.85m",
            "None; Tony's body had been enhanced by the modified techno-organic virus, Extremis, but it is currently inaccessible and inoperable.",
            "Tony has a genius level intellect that allows him to invent a wide range of sophisticated devices, specializing in advanced weapons and armor. He possesses a keen business mind.",
            "The Avengers, Initiative, Hellfire Club (outer circle), S.H.I.E.L.D., Illuminati, Thunderbolts, Force Works, Queen's Vengeance, Alcoholics Anonymous"
        )
        superheroes = listOf(superheroDetails)
    }

    @Test
    fun `should call 2 times to local data source when remote data is ok and the cache should be 1`() {
        `when`(superheroesLocalDataSource.getSuperheroes()).thenReturn(
            Either.Left(GetSuperheroesFailure.SuperheroesNotFound),
            Either.Right(superheroes)
        )
        `when`(superheroesRemoteDataSource.getSuperheroes()).then { Either.Right(superheroes) }

        superheroRepository.cachedSuperheroes.size `should be` 0

        superheroRepository.getSuperheroes().apply {
            isLeft `should be` false
            isRight `should be` true
            fold({
                Assert.fail()
            }, {
                it.`should not be empty`()
                it `should equal` superheroes
            })
        } // First call to API

        verify(superheroesLocalDataSource, times(2)).getSuperheroes()
        verify(superheroesRemoteDataSource).getSuperheroes()

        superheroRepository.cachedSuperheroes.size `should be` 1
    }


    @Test
    fun `should save all superhero`() {

        superheroRepository.cachedSuperheroes.size `should be` 0

        superheroRepository.saveSuperhero(superheroDetails)

        verify(superheroesLocalDataSource).saveSuperhero(superheroDetails)

        superheroRepository.cachedSuperheroes.size `should be` 1
    }

    @Test
    fun `should get superhero`() {
        `when`(superheroesLocalDataSource.getSuperhero(ArgumentMatchers.anyLong())).then {
            Either.Right(superheroDetails)
        }
        superheroRepository.cachedSuperheroes.size `should be` 0

        superheroRepository.getSuperhero(1L).apply {
            isLeft `should be` false
            isRight `should be` true
            fold({
                Assert.fail()
            }, {
                it `should be instance of` Superhero::class.java
                it `should equal` superheroDetails
            })
        }

        verify(superheroesLocalDataSource).getSuperhero(1L)

        superheroRepository.cachedSuperheroes.size `should be` 1
    }
}