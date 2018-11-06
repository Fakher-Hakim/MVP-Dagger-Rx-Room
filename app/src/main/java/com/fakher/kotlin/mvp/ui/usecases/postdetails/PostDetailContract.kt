package com.fakher.kotlin.mvp.ui.usecases.postdetails

import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.ui.BasePresenter

interface PostDetailContract {

    interface View {

        fun setLoadingVisibility(visible: Boolean)

        fun showComments(comments: List<Comment>)

        fun showError(message: String)
    }

    interface Presenter : BasePresenter {

        fun loadComments(postId: Int?)
    }
}