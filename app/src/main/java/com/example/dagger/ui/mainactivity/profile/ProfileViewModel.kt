package com.example.dagger.ui.mainactivity.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dagger.models.User
import com.example.dagger.SessionManager
import com.example.dagger.ui.auth.AuthResource

class ProfileViewModel(private val sessionManager: SessionManager) : ViewModel() {

    companion object {
        private val TAG = "ProfileViewModel"
    }

    init {
        Log.d(TAG, "Working..")
    }

    fun getUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}