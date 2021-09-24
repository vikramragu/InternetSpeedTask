package com.test.internettask.di.component

import android.app.Application
import com.test.internettask.InternetTaskApp
import com.test.internettask.di.factory.ViewModelFactoryModule
import com.test.internettask.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        UsecaseModule::class,
        NetworkModule::class,
        ComponentModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<InternetTaskApp> {


    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent

    }


}