package com.example.summarizednews.di

import com.example.core_util.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Dispatcher(Dispatcher.Type.IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}