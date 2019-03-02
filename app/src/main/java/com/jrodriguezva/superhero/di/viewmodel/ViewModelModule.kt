package com.jrodriguezva.superhero.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jrodriguezva.superhero.ui.superheroes.SuperheroDetailViewModel
import com.jrodriguezva.superhero.ui.superheroes.SuperheroesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SuperheroesViewModel::class)
    internal abstract fun bindMainViewModels(mainViewModel: SuperheroesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SuperheroDetailViewModel::class)
    internal abstract fun bindSuperheroDetailViewModel(mainViewModel: SuperheroDetailViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}