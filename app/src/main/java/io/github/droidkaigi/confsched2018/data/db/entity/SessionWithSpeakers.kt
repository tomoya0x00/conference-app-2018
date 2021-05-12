package io.github.droidkaigi.confsched2018.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SessionWithSpeakers(
        @Embedded var session: SessionEntity? = null,
        @Relation(
                parentColumn = "id",
                entityColumn = "sessionId",
                projection = ["speakerId"],
                entity = SessionSpeakerJoinEntity::class)
        var speakerIdList: List<String> = emptyList())
