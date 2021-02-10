package com.example.dagger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dagger.models.User
import com.example.dagger.ui.auth.AuthResource

class SessionManager {
    companion object {
        private val TAG: String = "Session Manager"
    }

    var cachedUser: MediatorLiveData<AuthResource<User>>

    init {
        cachedUser = MediatorLiveData()
    }

    fun authenticateWithId(source: LiveData<AuthResource<out User?>>) {
        //using mediator live data to obeserve other live data(user live data) and set the value to mediator live data(catchedUser)
        cachedUser.value = AuthResource.loading()
        cachedUser.addSource(source, androidx.lifecycle.Observer {
            cachedUser.value = it as AuthResource<User>?
            cachedUser.removeSource(source)
        })
    }

    fun logOut() {
        cachedUser.value = AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cachedUser
    }
}