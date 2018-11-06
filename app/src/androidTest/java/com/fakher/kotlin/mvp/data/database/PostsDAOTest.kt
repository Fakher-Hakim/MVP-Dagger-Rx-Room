package com.fakher.kotlin.mvp.data.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.fakher.kotlin.mvp.data.model.Post
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PostsDAOTest {

    private lateinit var mDatabase: PostsDb

    private val posts = listOf(Post(1, "title", "body"),
            Post(2, "title", "body"), Post(3, "title", "body"))

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
    fun insertPost_Get_By_Id() {

        mDatabase.postDao().insert(posts[0])

        val loaded = mDatabase.postDao().getById(1)

        Assert.assertEquals(posts[0], loaded)
    }
}