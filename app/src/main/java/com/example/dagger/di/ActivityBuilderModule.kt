package com.example.dagger.di

import com.example.dagger.di.auth.AuthModule
import com.example.dagger.di.auth.AuthScope
import com.example.dagger.di.auth.AuthViewModelsModule
import com.example.dagger.di.main.MainActivityModule
import com.example.dagger.di.main.MainActivityViewModelsModule
import com.example.dagger.di.main.MainScope
import com.example.dagger.ui.auth.AuthActivity
import com.example.dagger.ui.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @AuthScope
    @ContributesAndroidInjector(modules = [AuthViewModelsModule::class,AuthModule::class])
    abstract fun contributesAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainActivityFragmentBuilderModule::class,MainActivityViewModelsModule::class,MainActivityModule::class])
    abstract fun contributesMainActivity(): MainActivity
}
