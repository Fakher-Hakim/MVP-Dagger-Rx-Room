package com.fakher.kotlin.mvp.ui.usecases.posts

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.fakher.kotlin.mvp.R
import com.fakher.kotlin.mvp.app.MyApplication
import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.ui.BaseActivity
import com.fakher.kotlin.mvp.ui.usecases.login.LoginActivity
import com.fakher.kotlin.mvp.ui.usecases.postdetails.PostDetailActivity
import kotlinx.android.synthetic.main.activity_posts.*
import javax.inject.Inject

class PostsActivity : BaseActivity(), PostsContract.View, PostsAdapter.OnPostClick {

    @Inject
    lateinit var presenter: PostsPresenter

    @Inject
    lateinit var adapter: PostsAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        posts_rv.adapter = adapter
        posts_rv.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setLoadingVisibility(visible: Boolean) {
        posts_progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showPosts(posts: List<Post>) {
        adapter.setPosts(posts)
    }

    override fun showError(message: String) {
        Log.e(TAG, message)
        error_text_view.visibility = View.VISIBLE
        posts_rv.visibility = View.GONE
    }

    override fun onPostClick(view: View, position: Int) {
        val intent = Intent(this, PostDetailActivity::class.java)
        intent.putExtra(POST_INTENT, adapter.getitem(position))
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.posts_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            (application as MyApplication).releaseUserComponent()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun injectDependency() {
        (application as MyApplication).userComponent
                ?.plus(PostsModule(this))
                ?.inject(this)
    }

    companion object {
        val TAG = PostsActivity::class.java.simpleName
        val POST_INTENT = "POST_INTENT"
    }
}