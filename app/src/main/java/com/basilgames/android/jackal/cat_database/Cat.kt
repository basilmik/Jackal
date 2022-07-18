package com.basilgames.android.jackal.cat_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Cat (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var viewId: Int = 0,
    var imageRes: Int = 0,
    var topMargin: Float = 0F,
    var leftMargin: Float = 0F
)