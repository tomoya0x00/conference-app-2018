package io.github.droidkaigi.confsched2018.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import kotlinx.coroutines.flow.Flow

@Dao abstract class SpeakerDao {
    @CheckResult
    @Query("SELECT * FROM speaker")
    abstract fun getAllSpeaker(): Flow<List<SpeakerEntity>>

    @Query("DELETE FROM speaker")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(speakers: List<SpeakerEntity>)

    @Transaction open suspend fun clearAndInsert(newSessions: List<SpeakerEntity>) {
        deleteAll()
        insert(newSessions)
    }
}
