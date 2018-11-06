package com.fakher.kotlin.mvp.data.repository

import com.fakher.kotlin.mvp.data.ApiServices
import com.fakher.kotlin.mvp.data.database.PostsDb
import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.model.CommentDAO
import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.data.model.PostDAO
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PostRepositoryTest {

    private val posts = listOf(Post(1, "title", "body"),
            Post(2, "title", "body"), Post(3, "title", "body"))

    private val comments = listOf(Comment(1, 1, "name", "email", "body"),
            Comment(1, 1, "name", "email", "body"),
            Comment(1, 1, "name", "email", "body"))

    @Mock
    lateinit var db: PostsDb

    @Mock
    lateinit var postDAO: PostDAO
    @Mock

    lateinit var commentDAO: CommentDAO

    @Mock
    lateinit var apiServices: ApiServices

    private lateinit var postRepository: PostRepository
    private lateinit var postsTestSubscriber: TestSubscriber<List<Post>>
    private lateinit var commentsTestSubscriber: TestSubscriber<List<Comment>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(postDAO.getAll()).thenReturn(posts)
        `when`(commentDAO.getCommentsForPost(1)).thenReturn(comments)
        `when`(db.postDao()).thenReturn(postDAO)
        `when`(db.commentDao()).thenReturn(commentDAO)
        postRepository = PostRepository(apiServices, db)
        postsTestSubscriber = TestSubscriber()
        commentsTestSubscriber = TestSubscriber()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAll_UpdateForced() {

        `when`(apiServices.fetchPosts()).thenReturn(Flowable.just<List<Post>>(posts))

        postRepository.getAllPosts(true).subscribe(postsTestSubscriber)

        verify(apiServices).fetchPosts()
        verify(postDAO).insert(posts)
        postsTestSubscriber.assertValue(posts)
        verify(postDAO).insert(posts)
    }

    @Test
    fun getAll_NoUpdate() {

        `when`(postDAO.getAll()).thenReturn(posts)

        postRepository.getAllPosts(false).subscribe(postsTestSubscriber)

        verify(postDAO).getAll()
        postsTestSubscriber.assertValue(posts)
    }

    @Test
    fun getAll_NoUpdate_Empty_Database() {

        `when`(apiServices.fetchPosts()).thenReturn(Flowable.just<List<Post>>(posts))
        `when`(postDAO.getAll()).thenReturn(listOf())

        postRepository.getAllPosts(false).subscribe(postsTestSubscriber)
        verify(postDAO).getAll()
        postsTestSubscriber.assertValue(listOf())
        verify(apiServices).fetchPosts()
    }

    @Test
    fun getComments() {

        `when`(apiServices.fetchPostComments(1)).thenReturn(Flowable.just<List<Comment>>(comments))

        postRepository.getAllComments(1).subscribe(commentsTestSubscriber)

        verify(apiServices).fetchPostComments(1)
        commentsTestSubscriber.assertValue(comments)
    }
}