package pl.adrianczerwinski.common

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// github.com/Kotlin/kotlinx.coroutines/issues/1814 Cancellation exception needs to be rethrown
@Suppress("TooGenericExceptionCaught")
inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun <T> Flow<T>.toCatchingResult(): Flow<Result<T>> = this
    .map { resultOf { it } }
    .catch { emit(Result.failure(it)) }
