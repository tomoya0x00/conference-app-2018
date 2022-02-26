package io.github.droidkaigi.confsched2018.data.repository

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.model.Session
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val sessions: StateFlow<List<Session>>

    @CheckResult suspend fun refreshSessions()
    @CheckResult suspend fun favorite(session: Session.SpeechSession): Boolean
}
