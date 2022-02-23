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
import io.github.droidkaigi.confsched2018.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.await
import timber.log.Timber
import javax.inject.Inject

class SessionDataRepository @Inject constructor(
    private val api: DroidKaigiApi,
    private val sessionDatabase: SessionDatabase,
    private val favoriteDatabase: FavoriteDatabase,
    private val schedulerProvider: SchedulerProvider
) : SessionRepository {

    // Injecting by Dagger is preferable
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override val sessions: StateFlow<List<Session>> =
        Flowables.combineLatest(
            sessionDatabase.getAllSessions()
                .filter { it.isNotEmpty() }
                .doOnNext { if (DEBUG) Timber.d("getAllSessions") },
            sessionDatabase.getAllSpeaker()
                .filter { it.isNotEmpty() }
                .doOnNext { if (DEBUG) Timber.d("getAllSpeaker") },
            Flowable.concat(
                Flowable.just(listOf()),
                favoriteDatabase.favorites.onErrorReturn { listOf() }
            )
                .doOnNext { if (DEBUG) Timber.d("favorites") },
            { sessionEntities, speakerEntities, favList ->
                val firstDay = sessionEntities.first().session!!.stime.atJST().toLocalDate()
                val speakerSessions = sessionEntities
                    .map { it.toSession(speakerEntities, favList, firstDay) }
                    .sortedWith(compareBy(
                        { it.startTime.getTime() },
                        { it.room.id }
                    ))

                speakerSessions + specialSessions
            })
            .subscribeOn(schedulerProvider.io())
            .doOnNext {
                if (DEBUG) Timber.d("size:${it.size} current:${System.currentTimeMillis()}")
            }
            .asFlow()
            .stateIn(
                scope = scope,
                started = SharingStarted.Lazily,
                initialValue = emptyList(),
            )

    @VisibleForTesting
    val specialSessions: List<Session.SpecialSession> by lazy {
        SpecialSessions.getSessions()
    }

    @CheckResult override suspend fun favorite(session: Session.SpeechSession): Boolean =
        favoriteDatabase.favorite(session).await()

    @CheckResult override suspend fun refreshSessions() {
        api.getSessions()
            .doOnSuccess { response ->
                sessionDatabase.save(response)
            }
            .subscribeOn(schedulerProvider.io())
            .await()
    }

    companion object {
        const val DEBUG = false
    }
}
