package io.github.droidkaigi.confsched2018.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.github.droidkaigi.confsched2018.data.db.entity.SessionSpeakerJoinEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

@Dao abstract class SessionSpeakerJoinDao {
    @Language("RoomSql")
    @Transaction
    @CheckResult
    @Query("SELECT * FROM session")
    abstract fun getAllSessions(): Flowable<List<SessionWithSpeakers>>

    @Insert abstract fun insert(sessionSpeakerJoin: List<SessionSpeakerJoinEntity>)
}
