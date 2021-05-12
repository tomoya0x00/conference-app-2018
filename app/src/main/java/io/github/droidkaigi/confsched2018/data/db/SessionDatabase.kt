package io.github.droidkaigi.confsched2018.data.db

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.github.droidkaigi.confsched2018.data.db.entity.TopicEntity
import io.reactivex.Flowable

interface SessionDatabase {
    @CheckResult fun getAllSessions(): Flowable<List<SessionWithSpeakers>>
    @CheckResult fun getAllSpeaker(): Flowable<List<SpeakerEntity>>
    @CheckResult fun getAllRoom(): Flowable<List<RoomEntity>>
    @CheckResult fun getAllTopic(): Flowable<List<TopicEntity>>
    fun save(response: Response)
}
