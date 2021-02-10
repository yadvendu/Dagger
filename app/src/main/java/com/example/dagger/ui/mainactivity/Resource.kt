package com.example.dagger.ui.mainactivity

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource<T>(
    @field:NonNull @param:NonNull val status: Status,
    @field:Nullable @param:Nullable val data: T? = null,
    @field:Nullable @param:Nullable val message: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(@Nullable data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(@NonNull msg: String?, @Nullable data: T): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null , null)
        }
    }

}