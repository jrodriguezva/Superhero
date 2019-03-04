package com.jrodriguezva.superhero.ui.superheroes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.domain.usecase.GetSuperheroesUseCase
import com.jrodriguezva.superhero.domain.usecase.RefreshSuperheroesUseCase
import com.jrodriguezva.superhero.domain.funtional.fold
import javax.inject.Inject

class SuperheroesViewModel @Inject constructor(
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val refreshSuperheroes: RefreshSuperheroesUseCase
) : ViewModel() {
    val superheroes = MutableLiveData<List<Superhero>>().apply { value = emptyList() }
    val dataLoading = MutableLiveData<Boolean>()
    val isDataLoadingError = MutableLiveData<Boolean>()

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(superheroes) {
        it.isEmpty()
    }

    fun start() {
        loadSuperheroes(false)
    }

    fun refresh() {
        loadSuperheroes(true)
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the DataSource
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadSuperheroes(forceUpdate: Boolean, showLoadingUI: Boolean = true) {
        if (showLoadingUI) {
            dataLoading.value = true
        }
        if (forceUpdate) {
            refreshSuperheroes.execute()
        }

        getSuperheroesUseCase.execute { result ->
            result.fold({ showError(showLoadingUI) },
                {
                    superheroes.value = it
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
