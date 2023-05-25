package com.example.summarizednews.di

import com.example.news_data.api.NewsApi
import com.example.news_data.repository.NewsRepositoryImpl
import com.example.news_domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NewsDataModule {
    @Binds
    fun bindNewsApi(impl: NewsRepositoryImpl): NewsRepository

    companion object {
        @Provides
        @Singleton
        fun provideNewsApi(
            okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory
        ): NewsApi = Retrofit.Builder()
            .baseUrl("https://content.guardianapis.com")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(NewsApi::class.java)
    }
}