package com.fakher.kotlin.mvp.data.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.Post
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CommentsDAOTest {

    private lateinit var mDatabase: PostsDb

    private val comments = listOf(Comment(1, 1, "name", "email", "body"),
            Comment(1, 1, "name", "email", "body"))

    @Before
    fun setUp() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                PostsDb::class.java).build()
    }

    @After
    fun tearDown() {
        mDatabase.close()
    }

    @Test
    fun insertComment_Get_By_Id() {

        mDatabase.postDao().insert(Post(1, "title", "body"))
        mDatabase.commentDao().insert(comments[0])

        val loaded = mDatabase.commentDao().getById(1)

        Assert.assertEquals(comments[0], loaded)
    }

    @Test
    fun insertComment_Get_By_WrongId() {

        mDatabase.postDao().insert(Post(1, "title", "body"))
        mDatabase.commentDao().insert(comments[0])

        val loaded = mDatabase.commentDao().getById(-1)

        Assert.assertEquals(null, loaded)
    }
}