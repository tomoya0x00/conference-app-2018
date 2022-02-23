package io.github.droidkaigi.confsched2018.data.db.entity

import androidx.room.ColumnInfo

data class RoomEntity(
    @ColumnInfo(name = "room_id")
    var id: Int,
    @ColumnInfo(name = "room_name")
    var name: String
)
