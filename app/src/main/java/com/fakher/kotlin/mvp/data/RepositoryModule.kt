package com.fakher.kotlin.mvp.data

import com.fakher.kotlin.mvp.data.database.PostsDb
import com.fakher.kotlin.mvp.data.repository.PostRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesPostRepository(apiServices: ApiServices, db: PostsDb): PostRepository {
        return PostRepository(apiServices, db)
    }
}