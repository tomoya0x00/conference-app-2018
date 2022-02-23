package io.github.droidkaigi.confsched2018.presentation.sessions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2018.data.repository.SessionRepository
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.presentation.Result
import io.github.droidkaigi.confsched2018.presentation.common.mapper.toResult
import io.github.droidkaigi.confsched2018.util.defaultErrorHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllSessionsViewModel @Inject constructor(
    private val repository: SessionRepository,
) : ViewModel(), LifecycleObserver {
    private val mutableRefreshState: MutableSharedFlow<Result<Unit>> = MutableSharedFlow()
    val refreshResult: SharedFlow<Result<Unit>> = mutableRefreshState

    val sessions: StateFlow<Result<List<Session>>> by lazy {
        repository.sessions
            .toResult()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Result.success(emptyList())
            )
    }

    val isLoading: StateFlow<Boolean> by lazy {
        sessions.map {
            it.inProgress
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )
    }

    fun onFavoriteClick(session: Session.SpeechSession) {
        viewModelScope.launch {
            runCatching {
                repository.favorite(session)
            }.onFailure(defaultErrorHandler())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        refreshSessions()
    }

    private fun refreshSessions() {
        viewModelScope.launch {
            val result = runCatching {
                repository.refreshSessions()
            }.fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(it.message ?: "unknown", it) },
            )
            mutableRefreshState.tryEmit(result)
        }
    }

    fun onRetrySessions() {
        refreshSessions()
    }
}
