package com.example.dagger.di.auth

import com.example.dagger.SessionManager
import com.example.dagger.models.User
import com.example.dagger.network.auth.AuthApi
import com.example.dagger.ui.auth.AuthViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
object AuthModule {
    //Understanding Case
    //if two dependency of same type(for eg user in auth and main module),then to differentiate use @Named annotation
    @AuthScope
    @Provides
    @JvmStatic
    @Named("auth_user")
    fun provideUser(): User = User()

    //Practical Case
    @AuthScope
    @Provides
    @JvmStatic
    fun provideAuthApi(retrofit: Retrofit):AuthApi = retrofit.create(AuthApi::class.java)

    @AuthScope
    @Provides
    @JvmStatic
    fun providesAuthViewModel(authApi: AuthApi,sessionManager: SessionManager) = AuthViewModel(authApi,sessionManager)
}