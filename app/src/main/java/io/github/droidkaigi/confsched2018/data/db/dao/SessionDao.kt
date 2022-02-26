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
import kotlinx.coroutines.flow.Flow

@Dao abstract class SessionDao {
    @CheckResult
    @Query("SELECT * FROM session")
    abstract fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("DELETE FROM session")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(sessions: List<SessionEntity>)

    @Transaction open suspend fun clearAndInsert(newSessions: List<SessionEntity>) {
        deleteAll()
        insert(newSessions)
    }

    @CheckResult
    @Query("SELECT room_id, room_name FROM session GROUP BY room_id ORDER BY room_id")
    abstract fun getAllRoom(): Flow<List<RoomEntity>>

    @CheckResult
    @Query("SELECT topic_id, topic_name FROM session GROUP BY topic_id ORDER BY topic_id")
    abstract fun getAllTopic(): Flow<List<TopicEntity>>
}
