package com.example.dagger.di.main

import android.app.Application
import android.content.Context
import com.example.dagger.SessionManager
import com.example.dagger.network.main.MainApi
import com.example.dagger.ui.mainactivity.MainActivity
import com.example.dagger.ui.mainactivity.MainActivity_MembersInjector
import com.example.dagger.ui.mainactivity.posts.PostRecyclerViewAdapter
import com.example.dagger.ui.mainactivity.posts.PostViewModel
import com.example.dagger.ui.mainactivity.profile.ProfileViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainActivityModule {
    @MainScope
    @Provides
    @JvmStatic
    fun providesProfileViewModel(sessionManager: SessionManager) = ProfileViewModel(sessionManager)

    @MainScope
    @Provides
    @JvmStatic
    fun providesMainApi(retrofit: Retrofit) = retrofit.create(MainApi::class.java)

    @MainScope
    @Provides
    @JvmStatic
    fun providesPostViewModel(mainActivity: MainActivity,sessionManager: SessionManager,mainApi: MainApi) = PostViewModel(mainActivity,sessionManager,mainApi)

    @MainScope
    @Provides
    @JvmStatic
    fun providesPostRecyclerviewAdapter() = PostRecyclerViewAdapter()
}