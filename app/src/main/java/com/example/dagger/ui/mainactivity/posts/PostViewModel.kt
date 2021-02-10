package com.example.dagger.ui.mainactivity.posts

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.dagger.SessionManager
import com.example.dagger.models.Post
import com.example.dagger.models.User
import com.example.dagger.network.main.MainApi
import com.example.dagger.ui.auth.AuthResource
import com.example.dagger.ui.mainactivity.Resource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val context:Context,private val sessionManager: SessionManager,private val mainApi: MainApi):ViewModel() {

    private val post: MediatorLiveData<Resource<List<Post>>>

    companion object{
        private val TAG = "PostViewModel"
    }

    init {
        post = MediatorLiveData()
        Log.d(TAG,"Post View model working..")
    }

    fun fetchPost(id:String?){
        post.value = Resource.loading()
        val source = LiveDataReactiveStreams.fromPublisher(
            mainApi.getPostFromUser(id ?: "-1")
                .onErrorReturn {
                    val obj = Post(id = -1)
                    val list = ArrayList<Post>()
                    list.add(obj)
                    list
                }
                .map {
                    if (it.size > 0){
                        if (it[0].id == -1){
                            Resource.error("Something went wrong",null)
                        }
                    }

                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )
        post.addSource(source) {
            post.value = it
            post.removeSource(source)
        }
    }

    fun getUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }

    fun getPost():LiveData<Resource<List<Post>>>{
        return post
    }
}