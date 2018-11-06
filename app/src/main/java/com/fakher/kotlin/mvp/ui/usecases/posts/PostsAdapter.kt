package com.fakher.kotlin.mvp.ui.usecases.posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fakher.kotlin.mvp.R
import com.fakher.kotlin.mvp.data.model.Post

class PostsAdapter(var listener: OnPostClick) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var postList = mutableListOf<Post>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val itemView = inflater.inflate(R.layout.item_post_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: PostsAdapter.ViewHolder, position: Int) {
        val post = postList[position]

        holder.titleView.text = post.title
        holder.bodyView.text = post.body
    }

    fun setPosts(posts: List<Post>) {
        postList.clear()
        postList.addAll(posts)
        notifyDataSetChanged()
    }

    fun getitem(position: Int): Post {
        return postList[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.findViewById<TextView>(R.id.post_title)
        val bodyView = itemView.findViewById<TextView>(R.id.post_body)

        init {
            itemView.setOnClickListener {
                listener.onPostClick(itemView, adapterPosition)
            }
        }
    }

    interface OnPostClick {
        fun onPostClick(view: View, position: Int)
    }
}