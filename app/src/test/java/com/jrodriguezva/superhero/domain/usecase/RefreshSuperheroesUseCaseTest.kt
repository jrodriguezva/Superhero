package com.jrodriguezva.superhero.domain.usecase

import com.jrodriguezva.superhero.data.repository.SuperheroRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class RefreshSuperheroesUseCaseTest {

    @Test
    fun `should refresh data`() {
        val dataSource = Mockito.mock(SuperheroRepository::class.java)

        val refreshSuperheroesDataSource = RefreshSuperheroesUseCase(dataSource, Coroutine())

        refreshSuperheroesDataSource.execute()

        verify(dataSource).refreshSuperheroes()
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