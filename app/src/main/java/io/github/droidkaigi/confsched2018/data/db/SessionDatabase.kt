package io.github.droidkaigi.confsched2018.data.db

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.github.droidkaigi.confsched2018.data.db.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

interface SessionDatabase {
    @CheckResult fun getAllSessions(): Flow<List<SessionWithSpeakers>>
    @CheckResult fun getAllSpeaker(): Flow<List<SpeakerEntity>>
    @CheckResult fun getAllRoom(): Flow<List<RoomEntity>>
    @CheckResult fun getAllTopic(): Flow<List<TopicEntity>>
    suspend fun save(response: Response)
}
