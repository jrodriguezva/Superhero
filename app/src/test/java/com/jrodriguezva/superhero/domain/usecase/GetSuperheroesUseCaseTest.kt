package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.data.repository.SuperheroRepository
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.funtional.fold
import com.jrodriguezva.superhero.domain.model.Superhero
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be empty`
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class GetSuperheroesUseCaseTest {

    @Test
    fun `should get data from repository`() {
        val superheroes = listOf<Superhero>(Mockito.mock(Superhero::class.java))
        val dataSource = Mockito.mock(SuperheroRepository::class.java)
        `when`(dataSource.getSuperheroes()).then { Either.Right(superheroes) }

        val getSuperheroesDataSource = GetSuperheroesUseCase(dataSource, Coroutine())

        getSuperheroesDataSource.execute { result ->
            result.isLeft `should be`false
            result.isRight `should be`true
            result.fold({ Assert.fail() },
                { right ->
                    right.`should not be empty`()
                    right `should be`superheroes
                })
        }
        verify(dataSource).getSuperheroes()
        verifyNoMoreInteractions(dataSource)
    }

    class Coroutine : Executor {
        override fun uiExecute(uiFun: suspend () -> Unit) {
            runBlocking { uiFun() }
        }

        override fun asyncExecute(asyncFun: suspend () -> Unit) {
            runBlocking { asyncFun() }
        }

    }
}