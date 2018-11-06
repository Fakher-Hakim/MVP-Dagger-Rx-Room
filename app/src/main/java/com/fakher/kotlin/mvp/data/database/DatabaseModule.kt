package com.fakher.kotlin.mvp.data.database

import android.arch.persistence.room.Room
import android.content.Context
import com.fakher.kotlin.mvp.data.model.CommentDAO
import com.fakher.kotlin.mvp.data.model.PostDAO
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Named(DATABASE)
    internal fun provideDatabaseName(): String {
        return PostsDb.DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideDb(context: Context, @Named(DATABASE) databaseName: String): PostsDb {
        return Room.databaseBuilder(context, PostsDb::class.java, databaseName).build()
    }

    @Provides
    @Singleton
    fun providePostDao(postDb: PostsDb): PostDAO {
        return postDb.postDao()
    }

    @Provides
    @Singleton
    fun provideCommentDao(postDb: PostsDb): CommentDAO {
        return postDb.commentDao()
    }

    companion object {
        const val DATABASE = "database_name"
    }
}