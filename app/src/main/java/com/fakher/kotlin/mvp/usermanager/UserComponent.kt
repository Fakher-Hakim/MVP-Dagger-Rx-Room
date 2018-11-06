package com.fakher.kotlin.mvp.usermanager

import com.fakher.kotlin.mvp.ui.usecases.postdetails.PostsDetailComponent
import com.fakher.kotlin.mvp.ui.usecases.postdetails.PostsDetailModule
import com.fakher.kotlin.mvp.ui.usecases.posts.PostsComponent
import com.fakher.kotlin.mvp.ui.usecases.posts.PostsModule
import dagger.Subcomponent

@UserScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    fun plus(postsModule: PostsModule): PostsComponent

    fun plus(postDetailModule: PostsDetailModule): PostsDetailComponent
}