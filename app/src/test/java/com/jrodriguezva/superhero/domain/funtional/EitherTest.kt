package com.jrodriguezva.superhero.domain.funtional

import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import org.amshove.kluent.*
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.Mockito.mock


class EitherShould {
    @Test
    fun `success either should return right type`() {

        val superhero = mock(Superhero::class.java)
        val successResult = Either.Right(superhero)

        successResult `should be instance of`Either::class.java
        successResult.isRight `should be`true
        successResult.isLeft `should be`false
        successResult.fold({ fail() },
            { right ->
                right `should be instance of` Superhero::class.java
                right `should equal` superhero
            })

    }

    @Test
    fun `fail either should return left type`() {

        val failure = GetSuperheroesFailure.NetworkConnection
        val failResult = Either.Left(failure)

        failResult `should be instance of`Either::class.java
        failResult.isRight `should be`false
        failResult.isLeft `should be`true
        failResult.fold({ left ->
            left `should be instance of`GetSuperheroesFailure::class.java
            left `should be`failure
        }, { fail() })

    }

}