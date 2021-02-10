package com.example.dagger.di

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.dagger.R
import com.example.dagger.SessionManager
import com.example.dagger.models.User
import com.example.dagger.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {
    //Understanding Case
    //if two dependency of same type(for eg user in auth and main module),then to differentiate use @Named annotation
    @Singleton
    @Provides
    @JvmStatic
    @Named("app_user")
    fun provideUser(): User = User()

    //Just for practise
    @Provides
    @JvmStatic
    fun someString(): String = "abcd"

    @Provides
    @JvmStatic
    fun isAppNull(application: Application): Boolean {
        return application == null
    }

    //Real practical dependencies for this app
    @Singleton
    @Provides
    @JvmStatic
    fun providesRequestOptionForGlide(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGlideInstance(application: Application,requestOptions: RequestOptions):RequestManager{
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppDrawable(application: Application):Drawable?{
        return ContextCompat.getDrawable(application,R.drawable.login)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofitInstance():Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @JvmStatic
    fun providesSessionManager() = SessionManager()
}