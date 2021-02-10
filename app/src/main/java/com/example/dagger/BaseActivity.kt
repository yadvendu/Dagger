package com.example.dagger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.dagger.ui.auth.AuthActivity
import com.example.dagger.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var sessionManager: SessionManager

    companion object{
        private val TAG:String = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers(){
        sessionManager.getAuthUser().observe(this, Observer {
            if (it != null){
                when(it.status){
                    AuthResource.AuthStatus.LOADING ->{

                    }

                    AuthResource.AuthStatus.AUTHENTICATED ->{
                        //Toast.makeText(this,"Login Successful \n Email Id:${it.data?.email}", Toast.LENGTH_SHORT).show()
                    }

                    AuthResource.AuthStatus.NOT_AUTHENTICATED ->{
                        openActivity(this,AuthActivity::class.java) // if user not authorized navigate back to login screen
                    }

                    AuthResource.AuthStatus.ERROR ->{
                        Toast.makeText(this,"Something went wrong ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    open fun openActivity(context: Context,calledActivity: Class<*>?): Unit {
        val myIntent = Intent(this, calledActivity)
        this.startActivity(myIntent)
    }
}