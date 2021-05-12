package io.github.droidkaigi.confsched2018.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionEntity
import io.github.droidkaigi.confsched2018.data.db.entity.TopicEntity
import io.reactivex.Flowable

@Dao abstract class SessionDao {
    @CheckResult
    @Query("SELECT * FROM session")
    abstract fun getAllSessions(): Flowable<List<SessionEntity>>

    @Query("DELETE FROM session")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(sessions: List<SessionEntity>)

    @Transaction open fun clearAndInsert(newSessions: List<SessionEntity>) {
        deleteAll()
        insert(newSessions)
    }

    @CheckResult
    @Query("SELECT room_id, room_name FROM session GROUP BY room_id ORDER BY room_id")
    abstract fun getAllRoom(): Flowable<List<RoomEntity>>

    @CheckResult
    @Query("SELECT topic_id, topic_name FROM session GROUP BY topic_id ORDER BY topic_id")
    abstract fun getAllTopic(): Flowable<List<TopicEntity>>
}
