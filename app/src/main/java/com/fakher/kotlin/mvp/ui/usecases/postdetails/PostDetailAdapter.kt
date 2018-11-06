package com.fakher.kotlin.mvp.ui.usecases.postdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fakher.kotlin.mvp.R
import com.fakher.kotlin.mvp.data.model.Comment

class PostDetailAdapter : RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {

    var commentList = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val itemView = inflater.inflate(R.layout.item_comment_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = commentList.size

    override fun onBindViewHolder(holder: PostDetailAdapter.ViewHolder, position: Int) {
        val comment = commentList[position]

        holder.commentMail.text = comment.email
        holder.commentText.text = comment.body
    }

    fun setData(comments: List<Comment>) {
        commentList.clear()
        commentList.addAll(comments)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentMail = itemView.findViewById<TextView>(R.id.comment_mail)
        val commentText = itemView.findViewById<TextView>(R.id.comment_text)
    }
}