package com.example.dagger.models

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id")
    var id:String? = null,
    @SerializedName("username")
    var userName:String? = null,
    @SerializedName("email")
    var email:String? = null,
    @SerializedName("website")
    var website:String? = null
)