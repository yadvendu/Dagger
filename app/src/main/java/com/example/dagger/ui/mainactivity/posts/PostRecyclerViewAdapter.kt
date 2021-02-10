package com.example.dagger.ui.mainactivity.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger.R
import com.example.dagger.databinding.LayoutPostListItemBinding
import com.example.dagger.models.Post

class PostRecyclerViewAdapter() :
    RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {
    private lateinit var list: List<Post>
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutPostListItemBinding>(LayoutInflater.from(context),
            R.layout.layout_post_list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(val binding: LayoutPostListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(obj:Post){
            binding.textView.text = obj.body
        }
    }

    fun setAdapter(list: List<Post>,context: Context){
        this.list = list
        this.context = context
    }
}