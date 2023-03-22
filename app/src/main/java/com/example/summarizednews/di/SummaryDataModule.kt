package com.example.summarizednews.di

import com.example.summary_data.api.SummaryApi
import com.example.summary_data.repository.SummaryRepositoryImpl
import com.example.summary_domain.SummaryRepository
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
interface SummaryDataModule {
    @Binds
    fun bindSummaryRepository(impl: SummaryRepositoryImpl): SummaryRepository

    companion object {
        @Provides
        @Singleton
        fun provideSummaryApi(
            okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory
        ): SummaryApi = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(SummaryApi::class.java)
    }
}