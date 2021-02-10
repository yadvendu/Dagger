package com.example.dagger.di

import androidx.lifecycle.ViewModelProvider
import com.example.dagger.viewmodels.ViewModelsProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindFactory(modelsProviderFactory: ViewModelsProviderFactory):ViewModelProvider.Factory
}
