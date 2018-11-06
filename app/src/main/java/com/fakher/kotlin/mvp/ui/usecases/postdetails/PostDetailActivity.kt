package com.fakher.kotlin.mvp.ui.usecases.postdetails

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.fakher.kotlin.mvp.R
import com.fakher.kotlin.mvp.app.MyApplication
import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.ui.BaseActivity
import com.fakher.kotlin.mvp.ui.usecases.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_post_detail.*
import javax.inject.Inject


class PostDetailActivity : BaseActivity(), PostDetailContract.View {

    @Inject
    lateinit var presenter: PostDetailPresenter

    @Inject
    lateinit var adapter: PostDetailAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        post = intent?.extras?.getParcelable(PostsActivity.POST_INTENT)

        post_detail_title.text = post?.title
        post_detail_body.text = post?.body

        comments_rv.adapter = adapter
        comments_rv.layoutManager = layoutManager

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.comment_separator)?.let { itemDecorator.setDrawable(it) }

        comments_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        presenter.loadComments(post?.id)
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setLoadingVisibility(visible: Boolean) {
        post_detail_progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showComments(comments: List<Comment>) {
        adapter.setData(comments)
    }

    override fun showError(message: String) {
        Log.e(TAG, message)
    }

    override fun injectDependency() {
        (application as MyApplication).userComponent
                ?.plus(PostsDetailModule(this))
                ?.inject(this)
    }

    companion object {
        val TAG = PostDetailActivity::class.java.simpleName
    }
}