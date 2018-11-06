package com.fakher.kotlin.mvp.ui.usecases.posts

import com.fakher.kotlin.mvp.data.model.Post
import com.fakher.kotlin.mvp.data.repository.PostRepository
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class PostsPresenterTest {

    @Mock
    lateinit var postRepository: PostRepository
    @Mock
    lateinit var view: PostsContract.View

    private lateinit var presenter: PostsPresenter
    private val posts = listOf(Post(1, "title", "body"),
            Post(2, "title", "body"), Post(3, "title", "body"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PostsPresenter(postRepository, view,
                Schedulers.trampoline(), Schedulers.trampoline(), CompositeDisposable())
    }

    @After
    fun tearDown() {
        presenter.unSubscribe()
    }

    @Test
    fun loadPosts_Success() {

        Mockito.`when`(postRepository.getAllPosts(false)).thenReturn(Flowable.just(posts))

        presenter.loadPosts(false)
        verify(postRepository).getAllPosts(false)
        verify(view).setLoadingVisibility(true)
        verify(view).showPosts(posts)
        verify(view).setLoadingVisibility(false)
    }

    @Test
    fun loadPosts_ForceUpdate_Success() {

        Mockito.`when`(postRepository.getAllPosts(true)).thenReturn(Flowable.just(posts))

        presenter.loadPosts(true)
        verify(view).setLoadingVisibility(true)
        verify(postRepository).getAllPosts(true)
        verify(view).showPosts(posts)
        verify(view).setLoadingVisibility(false)
    }
}