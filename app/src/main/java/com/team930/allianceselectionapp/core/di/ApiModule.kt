package com.team930.allianceselectionapp.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.team930.allianceselectionapp.data.remote.Api
import com.team930.allianceselectionapp.data.source.remote.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(RequestInterceptor()).addInterceptor(interceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): Api =
        Retrofit.Builder()
            .baseUrl("https://scoutingbackend-381918.uc.r.appspot.com/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(okHttpClient)
            .build()
            .create(Api::class.java)

}