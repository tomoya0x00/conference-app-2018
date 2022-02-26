package io.github.droidkaigi.confsched2018.data.repository

import androidx.annotation.CheckResult
import androidx.annotation.VisibleForTesting
import io.github.droidkaigi.confsched2018.data.api.DroidKaigiApi
import io.github.droidkaigi.confsched2018.data.db.FavoriteDatabase
import io.github.droidkaigi.confsched2018.data.db.SessionDatabase
import io.github.droidkaigi.confsched2018.data.db.entity.mapper.toSession
import io.github.droidkaigi.confsched2018.data.db.fixeddata.SpecialSessions
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.util.ext.atJST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SessionDataRepository @Inject constructor(
    private val api: DroidKaigiApi,
    private val sessionDatabase: SessionDatabase,
    private val favoriteDatabase: FavoriteDatabase,
) : SessionRepository {

    // Injecting by Dagger is preferable
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override val sessions: StateFlow<List<Session>> =
        combine(
            sessionDatabase.getAllSessions()
                .filter { it.isNotEmpty() }
                .onEach { if (DEBUG) Timber.d("getAllSessions") },
            sessionDatabase.getAllSpeaker()
                .filter { it.isNotEmpty() }
                .onEach { if (DEBUG) Timber.d("getAllSpeaker") },
            favoriteDatabase.favorites
                .onStart { emit(emptyList()) }
                .catch { emit(emptyList()) }
                .onEach { if (DEBUG) Timber.d("favorites") }
        ) { sessionEntities, speakerEntities, favList ->
            withContext(Dispatchers.IO) { // Injecting by Dagger is preferable
                val firstDay = sessionEntities.first().session!!.stime.atJST().toLocalDate()
                val speakerSessions = sessionEntities
                    .map { it.toSession(speakerEntities, favList, firstDay) }
                    .sortedWith(compareBy(
                        { it.startTime.time },
                        { it.room.id }
                    ))

                speakerSessions + specialSessions
            }
        }.onEach {
            if (DEBUG) Timber.d("size:${it.size} current:${System.currentTimeMillis()}")
        }.stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList(),
        )

    @VisibleForTesting
    val specialSessions: List<Session.SpecialSession> by lazy {
        SpecialSessions.getSessions()
    }

    @CheckResult override suspend fun favorite(session: Session.SpeechSession): Boolean =
        favoriteDatabase.favorite(session)

    @CheckResult override suspend fun refreshSessions() {
        val response = api.getSessions()
        sessionDatabase.save(response)
    }

    companion object {
        const val DEBUG = false
    }
}
