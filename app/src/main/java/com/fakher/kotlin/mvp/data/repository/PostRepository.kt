package com.fakher.kotlin.mvp.data.repository

import android.annotation.SuppressLint
import com.fakher.kotlin.mvp.data.ApiServices
import com.fakher.kotlin.mvp.data.database.PostsDb
import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.CommentDAO
import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.data.model.PostDAO
import io.reactivex.Flowable

open class PostRepository(var apiServices: ApiServices, db: PostsDb) {

    private var postDao: PostDAO = db.postDao()
    private var commentDao: CommentDAO = db.commentDao()

    @SuppressLint("CheckResult")
    open fun getAllPosts(forceUpdate: Boolean): Flowable<List<Post>> {
        return if (forceUpdate) {
            refreshPosts()
        } else {

            return Flowable.fromCallable {
                postDao.getAll()
            }.doOnNext { posts ->
                if (posts.isEmpty()) {
                    refreshPosts()
                }
            }
        }
    }

    open fun getAllComments(postId: Int): Flowable<List<Comment>> {
        return apiServices.fetchPostComments(postId)
                .doOnNext { comments ->
                    commentDao.insert(comments)
                }
    }

    open fun savePosts(posts: List<Post>) {
        postDao.insert(posts)
    }

    private fun refreshPosts(): Flowable<List<Post>> {
        return apiServices.fetchPosts()
                .doOnNext { fetchedPosts ->
                    // Update database
                    savePosts(fetchedPosts)
                }
    }

    companion object {
        val TAG = PostRepository::class.java.simpleName
    }
}