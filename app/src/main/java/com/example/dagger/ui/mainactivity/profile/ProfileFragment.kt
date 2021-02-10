package com.example.dagger.ui.mainactivity.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dagger.models.User
import com.example.dagger.R
import com.example.dagger.databinding.FragmentProfileBinding
import com.example.dagger.ui.auth.AuthResource
import com.example.dagger.viewmodels.ViewModelsProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelsProviderFactory

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    companion object {
        private val TAG = "ProfileFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel::class.java)
        subscribeObserver()
    }

    //Always subscribe observer in this fashion when working with live data in fragments bcz of fragment lifecycle
    private fun subscribeObserver() {
        viewModel.getUser().removeObserver {
            viewLifecycleOwner
        }

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        val user = it.data
                        showUserInfoOnSuccess(user)
                    }

                    AuthResource.AuthStatus.ERROR -> {
                        showErrorOnFailure()
                    }

                    else -> {
                        Log.d(TAG, "Error occurred")
                    }
                }
            }
        })
    }

    private fun showUserInfoOnSuccess(user:User?){
        user?.run {
            binding.email.text = email
            binding.username.text = userName
            binding.website.text = website
        }
    }

    private fun showErrorOnFailure(){
        binding.username.visibility = View.GONE
        binding.website.visibility = View.GONE
        binding.email.text = getString(R.string.profile_screen_error_message)
    }
}
