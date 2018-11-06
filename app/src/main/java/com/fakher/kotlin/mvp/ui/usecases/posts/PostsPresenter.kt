package com.fakher.kotlin.mvp.ui.usecases.posts

import android.annotation.SuppressLint
import android.util.Log
import com.fakher.kotlin.mvp.app.SchedulerModule
import com.fakher.kotlin.mvp.data.repository.PostRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

open class PostsPresenter(private val postRepo: PostRepository, val view: PostsContract.View,
                          @Named(SchedulerModule.MAIN_SCHEDULERS) private val mainThread: Scheduler,
                          @Named(SchedulerModule.BACKGROUND_SCHEDULERS) private val backgroundThread: Scheduler,
                          private val compositeDisposable: CompositeDisposable) : PostsContract.Presenter {

    private var forceUpdate = true

    override fun subscribe() {
        loadPosts(forceUpdate)
    }

    @SuppressLint("LogNotTimber")
    override fun loadPosts(update: Boolean) {

        val disposable = postRepo.getAllPosts(update)
                .onBackpressureBuffer(BACKPRESSURE_MAX_BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .doOnSubscribe {
                    view.setLoadingVisibility(true)
                }
                .doOnError { throwable ->
                    Log.e(TAG, "Error while getting posts ${throwable.message}")
                    view.showError(throwable.message!!)
                    view.setLoadingVisibility(false)
                }
                .subscribe { posts ->
                    Log.d(TAG, "fetched posts number: ${posts.size}")
                    view.showPosts(posts)
                    forceUpdate = false
                    view.setLoadingVisibility(false)
                }
        compositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

    companion object {
        const val BACKPRESSURE_MAX_BUFFER = 100
        val TAG = PostsPresenter::class.java.simpleName
    }
}