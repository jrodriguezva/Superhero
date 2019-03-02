package com.jrodriguezva.superhero.di.module

import com.jrodriguezva.superhero.ui.superhero.SuperheroDetailFragment
import com.jrodriguezva.superhero.ui.superheroes.SuperheroesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): SuperheroesFragment

    @ContributesAndroidInjector
    abstract fun contributeSuperheroDetailFragment(): SuperheroDetailFragment

}