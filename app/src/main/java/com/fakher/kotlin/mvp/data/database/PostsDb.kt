package com.fakher.kotlin.mvp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.CommentDAO
import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.data.model.PostDAO

@Database(entities = [Post::class, Comment::class], version = 1)
abstract class PostsDb : RoomDatabase() {

    abstract fun postDao(): PostDAO

    abstract fun commentDao(): CommentDAO

    companion object {
        const val DATABASE_NAME = "mvp_database"
    }
}
