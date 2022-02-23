package io.github.droidkaigi.confsched2018.data.db.entity

import androidx.room.ColumnInfo

data class TopicEntity(
    @ColumnInfo(name = "topic_id")
    var id: Int,
    @ColumnInfo(name = "topic_name")
    var name: String
)
