package io.github.droidkaigi.confsched2018.presentation.common.mapper

import io.github.droidkaigi.confsched2018.presentation.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.toResult(): Flow<Result<T>> {
    return this.map { Result.success(it) }
        .catch { e -> emit(Result.failure(e.message ?: "unknown", e)) }
        .onStart { emit(Result.inProgress()) }
}
