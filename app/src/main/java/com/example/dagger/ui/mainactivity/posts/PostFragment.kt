package com.example.dagger.ui.mainactivity.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.dagger.R
import com.example.dagger.databinding.FragmentPostBinding
import com.example.dagger.ui.auth.AuthResource
import com.example.dagger.ui.mainactivity.Resource
import com.example.dagger.viewmodels.ViewModelsProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PostFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelsProviderFactory

    @Inject
    lateinit var adapter: PostRecyclerViewAdapter

    private lateinit var binding:FragmentPostBinding
    private lateinit var viewModel: PostViewModel

    companion object {
        private val TAG = "PostFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_post, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this,providerFactory).get(PostViewModel::class.java)
        subscribeObserver()
        showPost()
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
                        viewModel.fetchPost(it.data?.id)
                    }

                    AuthResource.AuthStatus.NOT_AUTHENTICATED ->{
                        showProgressbar(false)
                    }

                    AuthResource.AuthStatus.ERROR ->{
                        showProgressbar(false)
                        Toast.makeText(requireActivity(),"Something went wrong ${it.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    fun showPost(){
        viewModel.getPost().observe(this, Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    showProgressbar(true)
                }

                Resource.Status.SUCCESS ->{
                    showProgressbar(false)
                    val list = it.data
                    list?.let {
                        initRecyclerView()
                        adapter.setAdapter(it,requireActivity())
                        adapter.notifyDataSetChanged()
                    }
                    Log.d(TAG,it.data.toString())
                }

                Resource.Status.ERROR ->{
                    showProgressbar(false)
                    Toast.makeText(requireActivity(),"Something went wrong ${it.message}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun showProgressbar(isVisible:Boolean){
        if (isVisible){
            binding.progressCircular.visibility = View.VISIBLE
        }else{
            binding.progressCircular.visibility = View.INVISIBLE
        }
    }

    fun initRecyclerView(){
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }
}
