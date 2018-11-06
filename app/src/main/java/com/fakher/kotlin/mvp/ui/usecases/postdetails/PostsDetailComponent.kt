package com.fakher.kotlin.mvp.ui.usecases.postdetails

import com.fakher.kotlin.mvp.ui.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [PostsDetailModule::class])
interface PostsDetailComponent {

    fun inject(postDetailActivity: PostDetailActivity)
}