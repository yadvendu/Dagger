package com.example.dagger.network.auth

import com.example.dagger.models.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApi {
    @GET("users/{id}")
    fun getUsers(@Path("id") id:Int):Flowable<User>
}