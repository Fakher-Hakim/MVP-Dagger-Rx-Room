package com.fakher.kotlin.mvp.data

import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.Post
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/posts/")
    fun fetchPosts(): Flowable<List<Post>>

    @GET("/id")
    fun fetchPostById(id: Int): Single<Post>

    @GET("/comments")
    fun fetchPostComments(@Query("post_id") postId: Int): Flowable<List<Comment>>
}