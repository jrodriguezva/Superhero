package com.jrodriguezva.superhero.ui.superhero

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrodriguezva.superhero.LiveDataTestUtil
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroesFailure
import com.jrodriguezva.superhero.domain.usecase.GetSuperheroesUseCase
import com.jrodriguezva.superhero.domain.usecase.RefreshSuperheroesUseCase
import com.jrodriguezva.superhero.ui.superheroes.SuperheroesViewModel
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be empty`
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SuperheroesViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var getSuperheroesUseCase: GetSuperheroesUseCase
    @Mock
    private lateinit var refreshSuperheroesUseCase: RefreshSuperheroesUseCase
    @Captor
    private lateinit var loadEitherCaptor: ArgumentCaptor<(Either<GetSuperheroesFailure, List<Superhero>>) -> Unit>
    private lateinit var superheroesViewModel: SuperheroesViewModel

    @Before
    fun setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        superheroesViewModel = SuperheroesViewModel(getSuperheroesUseCase, refreshSuperheroesUseCase)
    }

    @Test
    fun `load all superheroes from repository`() {
        val superheroDetails = Superhero(
            1,
            "Iron Man",
            "photo",
            "Anthony Edward 'Tony' Stark",
            "1.85m",
            "None; Tony's body had been enhanced by the modified techno-organic virus, Extremis, but it is currently inaccessible and inoperable.",
            "Tony has a genius level intellect that allows him to invent a wide range of sophisticated devices, specializing in advanced weapons and armor. He possesses a keen business mind.",
            "The Avengers, Initiative, Hellfire Club (outer circle), S.H.I.E.L.D., Illuminati, Thunderbolts, Force Works, Queen's Vengeance, Alcoholics Anonymous"
        )
        val superheroes = listOf(superheroDetails)
        superheroesViewModel.start()
        verifyNoMoreInteractions(refreshSuperheroesUseCase)
        verify(getSuperheroesUseCase).execute(capture(loadEitherCaptor))

        // Then progress indicator is shown
        assertTrue(LiveDataTestUtil.getValue(superheroesViewModel.dataLoading))
        loadEitherCaptor.value.invoke(Either.Right(superheroes))

        // Then progress indicator is hidden
        assertFalse(LiveDataTestUtil.getValue(superheroesViewModel.dataLoading))

        // And data loaded
        LiveDataTestUtil.getValue(superheroesViewModel.superheroes).`should not be empty`()
        LiveDataTestUtil.getValue(superheroesViewModel.superheroes) `should be` superheroes
    }
}