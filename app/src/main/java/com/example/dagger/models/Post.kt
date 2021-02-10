package com.example.dagger.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("body")
    var body: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("userId")
    var userId: Int? = null
)