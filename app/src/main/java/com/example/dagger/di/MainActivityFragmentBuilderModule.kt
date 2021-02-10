package com.example.dagger.di

import com.example.dagger.ui.mainactivity.posts.PostFragment
import com.example.dagger.ui.mainactivity.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment():ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributesPostFragment():PostFragment
}