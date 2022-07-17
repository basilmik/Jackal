package com.basilgames.android.jackal.database

import androidx.room.TypeConverter
import java.util.*

class TileTypeConverters {
    @TypeConverter

    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}