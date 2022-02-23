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
import io.github.droidkaigi.confsched2018.util.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.awaitFirst
import javax.inject.Inject

class AllSessionsViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mutableRefreshState: MutableSharedFlow<Result<Unit>> = MutableSharedFlow()
    val refreshResult: SharedFlow<Result<Unit>> = mutableRefreshState

    val sessions: StateFlow<Result<List<Session>>> by lazy {
        repository.sessions
            .toResult(schedulerProvider)
            .asFlow()
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
        val favoriteSingle: Single<Boolean> = repository.favorite(session)
        favoriteSingle
            .subscribeBy(onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        refreshSessions()
    }

    private fun refreshSessions() {
        viewModelScope.launch {
            runCatching {
                repository
                    .refreshSessions()
                    .toResult<Unit>(schedulerProvider)
                    .awaitFirst()
            }.onSuccess { result ->
                mutableRefreshState.tryEmit(result)
            }.onFailure(defaultErrorHandler())
        }
    }

    fun onRetrySessions() {
        refreshSessions()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
