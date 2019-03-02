package com.jrodriguezva.superhero.ui.superheroes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.usecase.GetSuperheroByIdUseCase
import com.jrodriguezva.superhero.domain.usecase.RefreshSuperheroesUseCase
import com.jrodriguezva.superhero.utils.fold
import javax.inject.Inject

class SuperheroDetailViewModel @Inject constructor(
    private val getSuperheroUseCase: GetSuperheroByIdUseCase
) : ViewModel() {
    val superhero = MutableLiveData<Superhero>()
    val dataLoading = MutableLiveData<Boolean>()
    val isDataLoadingError = MutableLiveData<Boolean>()

    fun start(superheroId: Long) {
        loadSuperhero(superheroId)
    }

    /**
     * @param superheroId   Pass superhero id
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadSuperhero(superheroId: Long, showLoadingUI: Boolean = true) {
        if (showLoadingUI) {
            dataLoading.value = true
        }

        getSuperheroUseCase.execute(superheroId) { result ->
            result.fold({ showError(showLoadingUI) },
                {
                    superhero.value = it
                    dataLoading.value = false
                })
        }
    }

    private fun showError(showLoadingUI: Boolean) {
        if (showLoadingUI) {
            dataLoading.value = false
        }
        isDataLoadingError.value = true
    }
}
