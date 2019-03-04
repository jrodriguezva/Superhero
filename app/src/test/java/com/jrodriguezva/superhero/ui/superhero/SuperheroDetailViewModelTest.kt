package com.jrodriguezva.superhero.ui.superhero

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrodriguezva.superhero.LiveDataTestUtil
import com.jrodriguezva.superhero.domain.funtional.Either
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.source.GetSuperheroFailure
import com.jrodriguezva.superhero.domain.usecase.GetSuperheroByIdUseCase
import com.nhaarman.mockitokotlin2.capture
import org.amshove.kluent.`should be`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify

class SuperheroDetailViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var getSuperheroByIdUseCase: GetSuperheroByIdUseCase
    @Captor
    private lateinit var loadEitherCaptor: ArgumentCaptor<(Either<GetSuperheroFailure, Superhero>) -> Unit>
    private lateinit var superheroDetailViewModel: SuperheroDetailViewModel

    @Before
    fun setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        superheroDetailViewModel = SuperheroDetailViewModel(getSuperheroByIdUseCase)
    }

    @Test
    fun `load superhero from repository`() {
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
        superheroDetailViewModel.start(1)

        verify(getSuperheroByIdUseCase).execute(ArgumentMatchers.eq(1L), capture(loadEitherCaptor))

        // Then progress indicator is shown
        assertTrue(LiveDataTestUtil.getValue(superheroDetailViewModel.dataLoading))
        loadEitherCaptor.value.invoke(Either.Right(superheroDetails))

        // Then progress indicator is hidden
        assertFalse(LiveDataTestUtil.getValue(superheroDetailViewModel.dataLoading))

        // And data loaded
        LiveDataTestUtil.getValue(superheroDetailViewModel.superhero) `should be` superheroDetails
    }
}