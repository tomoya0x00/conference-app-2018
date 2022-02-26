package io.github.droidkaigi.confsched2018.data.db

import androidx.annotation.CheckResult
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSessionEntities
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSessionSpeakerJoinEntities
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSpeakerEntities
import io.github.droidkaigi.confsched2018.data.db.dao.SessionDao
import io.github.droidkaigi.confsched2018.data.db.dao.SessionSpeakerJoinDao
import io.github.droidkaigi.confsched2018.data.db.dao.SpeakerDao
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.github.droidkaigi.confsched2018.data.db.entity.TopicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val sessionDao: SessionDao,
    private val speakerDao: SpeakerDao,
    private val sessionSpeakerJoinDao: SessionSpeakerJoinDao
) : SessionDatabase {
    @CheckResult override fun getAllSessions(): Flow<List<SessionWithSpeakers>> =
        sessionSpeakerJoinDao.getAllSessions()

    @CheckResult
    override fun getAllSpeaker(): Flow<List<SpeakerEntity>> = speakerDao.getAllSpeaker()

    @CheckResult override fun getAllRoom(): Flow<List<RoomEntity>> = sessionDao.getAllRoom()

    @CheckResult override fun getAllTopic(): Flow<List<TopicEntity>> = sessionDao.getAllTopic()

    override suspend fun save(response: Response) {
        database.withTransaction {
            speakerDao.clearAndInsert(response.speakers.toSpeakerEntities())
            val sessions = response.sessions
            val sessionEntities = sessions.toSessionEntities(response.categories, response.rooms)
            sessionDao.clearAndInsert(sessionEntities)
            sessionSpeakerJoinDao.insert(sessions.toSessionSpeakerJoinEntities())
        }
    }
}
