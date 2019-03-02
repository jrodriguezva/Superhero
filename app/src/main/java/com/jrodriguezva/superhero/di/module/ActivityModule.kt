package com.jrodriguezva.superhero.di.module

import com.jrodriguezva.superhero.ui.MainActivity
import com.jrodriguezva.superhero.ui.SuperheroDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    internal abstract fun contributeSuperheroDetailActivity(): SuperheroDetailActivity

}