package com.basilgames.android.jackal.cat_database

import androidx.room.TypeConverter
import java.util.*

class CatTypeConverters {
    @TypeConverter

    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}