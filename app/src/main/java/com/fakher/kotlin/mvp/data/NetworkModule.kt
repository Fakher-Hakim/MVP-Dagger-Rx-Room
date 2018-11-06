package com.fakher.kotlin.mvp.data

import com.fakher.kotlin.mvp.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule() {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {

            //Add body logging interceptor
            val bodyLogging = HttpLoggingInterceptor()
            bodyLogging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(bodyLogging)
        }

        //Setting connection and read timeout for 5 seconds.
        builder.connectTimeout((5 * 60 * 1000).toLong(), TimeUnit.MILLISECONDS)
                .readTimeout((5 * 60 * 1000).toLong(), TimeUnit.MILLISECONDS)

        return builder.build()
    }

    @Inject
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(okHttpClient)
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(restAdapter: Retrofit): ApiServices {

        return restAdapter.create<ApiServices>(ApiServices::class.java)
    }

    companion object {
        const val END_POINT = "https://jsonplaceholder.typicode.com/"
    }
}