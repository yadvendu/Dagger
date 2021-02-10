package com.example.dagger.network.main

import com.example.dagger.models.Post
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("posts")
    fun getPostFromUser(
        @Query("userId") id: String
    ): Flowable<List<Post>>
}