package com.example.dagger.ui.auth

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class AuthResource<T>(
    @field:NonNull @param:NonNull val status: AuthStatus,
    @field:Nullable @param:Nullable val data: T ? = null,
    @field:Nullable @param:Nullable val message: String? = null
) {

    enum class AuthStatus {
        AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED
    }

    companion object {
        fun <T> authenticated(@Nullable data: T): AuthResource<T> {
            return AuthResource(AuthStatus.AUTHENTICATED, data, null)
        }

        fun <T> error(@NonNull msg: String?, @Nullable data: T): AuthResource<T> {
            return AuthResource(AuthStatus.ERROR, data, msg)
        }

        fun <T> loading(): AuthResource<T> {
            return AuthResource(AuthStatus.LOADING, null, null)
        }

        fun <T> logout(): AuthResource<T> {
            return AuthResource<T>(AuthStatus.NOT_AUTHENTICATED, null, null)
        }
    }

}