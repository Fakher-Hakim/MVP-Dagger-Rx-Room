package com.fakher.kotlin.mvp.ui.usecases.postdetails

import android.support.v7.widget.LinearLayoutManager
import com.fakher.kotlin.mvp.app.SchedulerModule
import com.fakher.kotlin.mvp.data.repository.PostRepository
import com.fakher.kotlin.mvp.ui.ActivityScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@Module
class PostsDetailModule(private val postDetailActivity: PostDetailActivity) {

    @Inject
    @Provides
    @ActivityScope
    fun providesPostDetailPresenter(postRepository: PostRepository,
                                    @Named(SchedulerModule.MAIN_SCHEDULERS) mainThread: Scheduler,
                                    @Named(SchedulerModule.BACKGROUND_SCHEDULERS) backgroundThread: Scheduler) =
            PostDetailPresenter(postRepository, postDetailActivity, mainThread, backgroundThread, CompositeDisposable())

    @Provides
    @ActivityScope
    fun providesPostsAdapter(): PostDetailAdapter {
        return PostDetailAdapter()
    }

    @Provides
    @ActivityScope
    fun providesLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(postDetailActivity)
    }
}
