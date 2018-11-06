package com.fakher.kotlin.mvp.ui.usecases.postdetails

import com.fakher.kotlin.mvp.data.model.Comment
import com.fakher.kotlin.mvp.data.repository.PostRepository
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PostDetailPresenterTest {

    @Mock
    lateinit var postRepository: PostRepository
    @Mock
    lateinit var view: PostDetailContract.View

    private var immediateScheduler = Schedulers.trampoline()

    private var compositeDisposable = CompositeDisposable()
    private lateinit var presenter: PostDetailPresenter
    private val comments = listOf(Comment(1, 1, "name", "email", "body"),
            Comment(1, 1, "name", "email", "body"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = PostDetailPresenter(postRepository, view, immediateScheduler, immediateScheduler, compositeDisposable)
    }

    @After
    fun tearDown() {
        presenter.unSubscribe()
    }

    @Test
    fun loadComments_ValidId() {
        Mockito.`when`(postRepository.getAllComments(1)).thenReturn(Flowable.just(comments))

        presenter.loadComments(1)
        Mockito.verify(view).setLoadingVisibility(true)
        Mockito.verify(view).showComments(comments)
        Mockito.verify(view).setLoadingVisibility(false)
    }
}