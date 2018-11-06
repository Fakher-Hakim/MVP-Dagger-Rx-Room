package com.fakher.kotlin.mvp.ui.usecases.posts

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
class PostsModule(private val postsActivity: PostsActivity) {

    @Inject
    @Provides
    @ActivityScope
    fun providesPostsPresenter(postRepo: PostRepository, @Named(SchedulerModule.MAIN_SCHEDULERS) mainThread: Scheduler,
                               @Named(SchedulerModule.BACKGROUND_SCHEDULERS) backgroundThread: Scheduler) = PostsPresenter(postRepo, postsActivity, mainThread, backgroundThread, CompositeDisposable())

    @Provides
    @ActivityScope
    fun providesPostsAdapter(): PostsAdapter {
        return PostsAdapter(postsActivity)
    }

    @Provides
    @ActivityScope
    fun providesLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(postsActivity)
    }
}