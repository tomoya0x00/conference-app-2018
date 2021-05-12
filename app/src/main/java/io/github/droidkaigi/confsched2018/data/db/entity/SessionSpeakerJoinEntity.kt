package io.github.droidkaigi.confsched2018.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "session_speaker_join", primaryKeys = ["sessionId", "speakerId"],
        foreignKeys = [
            (ForeignKey(
                    entity = SessionEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("sessionId"),
                    onDelete = CASCADE)),
            (ForeignKey(
                    entity = SpeakerEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("speakerId"),
                    onDelete = CASCADE))]
)
class SessionSpeakerJoinEntity(
        val sessionId: String,
        @ColumnInfo(index = true)
        val speakerId: String)
