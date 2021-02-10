package com.example.dagger.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.dagger.models.User
import com.example.dagger.SessionManager
import com.example.dagger.network.auth.AuthApi
import io.reactivex.schedulers.Schedulers


class AuthViewModel(val authApi: AuthApi,val sessionManager: SessionManager) : ViewModel() {

    init {
        Log.d("viewModel", "View Model is working..")

        if (authApi != null) {
            Log.d("authapi", "auth api is not null")
        }
    }

    //converting flowables to observables using retrofit and rxjava
//    fun getEmailId(){
//        authApi.getUsers(1)
//            .toObservable()
//            .subscribeOn(Schedulers.io())
//            .subscribe(object :Observer<Users>{
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: Users) {
//                    Log.d("onNext:",t.email)
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d("onError:","email error")
//                }
//
//            })
//    }

    fun getUserAndAuthenticate(id: Int) {
        //using mediator live data to obeserve other live data(user live data) and set the value to mediator live data(authUser)
        sessionManager.authenticateWithId(queryUserId(id))
    }

    fun queryUserId(userId:Int): LiveData<AuthResource<out User?>> {
        //By using from publishers flowables are directly converted into live data
        //i.e data coming from retrofit call are converted into live data directly
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUsers(userId)
                .onErrorReturn {
                    Log.d("retrofit",it.localizedMessage)
                    val errorUser =
                        User() // passing a null user as default value of User data class for all fields are null
                    errorUser
                }
                .map {
                    if (it.id == null) {
                        AuthResource.error(":Check if number is between 1 and 10", null)
                    } else {
                        AuthResource.authenticated(it)
                    }
                }
                .subscribeOn(Schedulers.io())
        )
    }

    fun getUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}