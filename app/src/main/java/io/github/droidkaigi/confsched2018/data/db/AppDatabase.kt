package io.github.droidkaigi.confsched2018.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.droidkaigi.confsched2018.data.db.dao.SessionDao
import io.github.droidkaigi.confsched2018.data.db.dao.SessionSpeakerJoinDao
import io.github.droidkaigi.confsched2018.data.db.dao.SpeakerDao
import io.github.droidkaigi.confsched2018.data.db.entity.SessionEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionSpeakerJoinEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.github.droidkaigi.confsched2018.data.db.entity.mapper.Converters

@Database(
        entities = [
            (SessionEntity::class),
            (SpeakerEntity::class),
            (SessionSpeakerJoinEntity::class)
        ],
        version = 9
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao
    abstract fun sessionSpeakerDao(): SessionSpeakerJoinDao
}
