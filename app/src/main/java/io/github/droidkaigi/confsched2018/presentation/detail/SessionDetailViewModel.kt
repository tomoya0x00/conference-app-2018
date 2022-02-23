package io.github.droidkaigi.confsched2018.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2018.data.repository.SessionRepository
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.presentation.Result
import io.github.droidkaigi.confsched2018.presentation.common.mapper.toResult
import io.github.droidkaigi.confsched2018.util.defaultErrorHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionDetailViewModel @Inject constructor(
    private val repository: SessionRepository,
) : ViewModel() {
    val sessions: StateFlow<Result<List<Session.SpeechSession>>> by lazy {
        repository.sessions
            .map { sessions ->
                sessions
                    .filterIsInstance<Session.SpeechSession>()
            }
            .toResult()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Result.success(emptyList())
            )
    }

    fun onFavoriteClick(session: Session.SpeechSession) {
        viewModelScope.launch {
            runCatching {
                repository.favorite(session)
            }.onFailure(defaultErrorHandler())
        }
    }
}
