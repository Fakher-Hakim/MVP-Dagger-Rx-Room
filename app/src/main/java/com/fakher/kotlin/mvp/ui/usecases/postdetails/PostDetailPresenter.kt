package com.fakher.kotlin.mvp.ui.usecases.postdetails

import android.util.Log
import com.fakher.kotlin.mvp.app.SchedulerModule
import com.fakher.kotlin.mvp.data.repository.PostRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

class PostDetailPresenter(private val postRepository: PostRepository, val view: PostDetailContract.View,
                          @Named(SchedulerModule.MAIN_SCHEDULERS) private val mainThread: Scheduler,
                          @Named(SchedulerModule.BACKGROUND_SCHEDULERS) private val backgroundThread: Scheduler,
                          private val compositeDisposable: CompositeDisposable) : PostDetailContract.Presenter {

    override fun loadComments(postId: Int?) {
        val disposable = postId?.let { id ->
            postRepository.getAllComments(id)
                    .subscribeOn(backgroundThread)
                    .onBackpressureBuffer(100)
                    .observeOn(mainThread)
                    .subscribe({ comments ->
                        view.setLoadingVisibility(true)
                        Log.d(TAG, "fetched posts number: ${comments.size}")
                        view.showComments(comments)
                    }, {
                        Log.e(TAG, "Error while getting posts")
                        it.message?.let { throwable -> view.showError(throwable) }
                    }, {
                        view.setLoadingVisibility(false)
                    })
        }
        disposable?.let { compositeDisposable.add(it) }
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

    companion object {
        val TAG = PostDetailPresenter::class.java.simpleName
    }
}