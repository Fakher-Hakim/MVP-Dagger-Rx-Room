package com.fakher.kotlin.mvp.ui.usecases.posts

import com.fakher.kotlin.mvp.ui.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [PostsModule::class])
interface PostsComponent {

    fun inject(postsActivity: PostsActivity)
}