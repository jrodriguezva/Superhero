package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.data.repository.SuperheroRepository
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.funtional.fold
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroFailure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class GetSuperheroByIdUseCaseTest {

    @Test
    fun `should get data from repository`() {
        val superhero = Mockito.mock(Superhero::class.java)
        val dataSource = Mockito.mock(SuperheroRepository::class.java)
        `when`(dataSource.getSuperhero(any())).then { Either.Right(superhero) }

        val getSuperheroDataSource = GetSuperheroByIdUseCase(dataSource, Coroutine())

        getSuperheroDataSource.execute(2) { result ->
            result.isLeft `should be`false
            result.isRight `should be`true
            result.fold({ Assert.fail() },
                { right ->
                    right `should be instance of`Superhero::class.java
                    right `should be`superhero
                })
        }

        verify(dataSource).getSuperhero(any())
        verifyNoMoreInteractions(dataSource)
    }

    @Test
    fun `should get error when no data from repository`() {
        val dataSource = Mockito.mock(SuperheroRepository::class.java)
        `when`(dataSource.getSuperhero(-1)).then { Either.Left(GetSuperheroFailure.SuperheroNotFound) }

        val getSuperheroDataSource = GetSuperheroByIdUseCase(dataSource, Coroutine())

        getSuperheroDataSource.execute(-1) { result ->
            result.isRight `should be`true
            result.isLeft `should be`false
            result.fold({ it `should be instance of`GetSuperheroFailure.SuperheroNotFound::class.java },
                { Assert.fail() })
        }

        verify(dataSource).getSuperhero(-1)
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