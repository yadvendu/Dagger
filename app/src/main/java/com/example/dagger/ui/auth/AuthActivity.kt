package com.example.dagger.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.dagger.R
import com.example.dagger.databinding.ActivityAuthBinding
import com.example.dagger.models.User
import com.example.dagger.ui.mainactivity.MainActivity
import com.example.dagger.viewmodels.ViewModelsProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import javax.inject.Inject
import javax.inject.Named

class AuthActivity : DaggerAppCompatActivity() {

    private val TAG = "AuthActivity"
    lateinit var binding: ActivityAuthBinding

    //Understanding Case
    //Same dependency of different scope when needed to use then used named annotation
    //with string value same as those provided in auth and app module
    @field:[Inject Named("app_user")]
    lateinit var userOne:User

    @field:[Inject Named("auth_user")]
    lateinit var userTwo:User

    //For practise
    @Inject
    lateinit var msg: String

    @set:Inject //When using with primitive types use @set:Inject
    var isAppNull: Boolean? = null

    //Practical use dependency injection for this app
    @set:Inject
    var logo: Drawable? = null

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelsProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        //For practise
        Log.d(TAG, "onCreate : ${msg}")
        Log.d(TAG, "onCreate : Is App Null -> ${isAppNull}")

        //Practical Implementation for the app
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)
        setLoginImage()

        binding.loginButton.setOnClickListener {
            binding.enterText.onEditorAction(EditorInfo.IME_ACTION_DONE) // to hide keyboard on button click
            attemptLogin()
        }

        subscribeObserver()

        Log.d(TAG, "${userOne} ${userTwo}")
    }

    fun setLoginImage() {
        requestManager.load(logo)
            .into(binding.loginImage)
    }

    fun subscribeObserver() {
       viewModel.getUser().observe(this, Observer {
           if (it != null){
               when(it.status){
                   AuthResource.AuthStatus.LOADING ->{
                       showProgressbar(true)
                   }

                   AuthResource.AuthStatus.AUTHENTICATED ->{
                       showProgressbar(false)
                       navigateToMainScreen()
                       //Toast.makeText(this,"Login Successful \n Email Id:${it.data?.email}",Toast.LENGTH_SHORT).show()
                   }

                   AuthResource.AuthStatus.NOT_AUTHENTICATED ->{
                       showProgressbar(false)
                   }

                   AuthResource.AuthStatus.ERROR ->{
                       showProgressbar(false)
                       Toast.makeText(this,"Something went wrong ${it.message}",Toast.LENGTH_SHORT).show()
                   }
               }
           }
       })
    }

    fun attemptLogin() {
        if (!(TextUtils.isEmpty(binding.enterText.text))) {
            viewModel.getUserAndAuthenticate(binding.enterText.text.toString().trim().toInt())
        }
    }

    fun showProgressbar(isVisible:Boolean){
        if (isVisible){
            binding.progressCircular.visibility = View.VISIBLE
        }else{
            binding.progressCircular.visibility = View.INVISIBLE
        }
    }

    fun navigateToMainScreen(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
