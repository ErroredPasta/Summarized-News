package com.example.core_util

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher(val type: Type) {
    enum class Type {
        MAIN, IO, DEFAULT, UNCONFINED
    }
}