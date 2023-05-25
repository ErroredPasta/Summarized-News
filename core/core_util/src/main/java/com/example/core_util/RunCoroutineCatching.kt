package com.example.core_util

import kotlin.coroutines.cancellation.CancellationException


inline fun <R> runCoroutineCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

inline fun <T, R> T.runCoroutineCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Throwable) {
        Result.failure(e)
    }
}