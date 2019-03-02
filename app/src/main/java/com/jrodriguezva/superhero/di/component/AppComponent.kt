package com.jrodriguezva.superhero.di.component

import android.app.Application
import com.jrodriguezva.superhero.di.module.ActivityModule
import com.jrodriguezva.superhero.di.module.AppModule
import com.jrodriguezva.superhero.di.module.NetworkModule
import com.jrodriguezva.superhero.di.module.PersistenceModule
import com.jrodriguezva.superhero.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        PersistenceModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}