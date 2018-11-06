package com.fakher.kotlin.mvp.ui.usecases.posts

import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.ui.BasePresenter

interface PostsContract {

    interface View {

        fun setLoadingVisibility(visible: Boolean)

        fun showPosts(posts: List<Post>)

        fun showError(message: String)
    }

    interface Presenter : BasePresenter {

        fun loadPosts(update: Boolean)
    }
}