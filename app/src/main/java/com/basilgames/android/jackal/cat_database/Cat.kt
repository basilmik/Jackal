package com.basilgames.android.jackal.cat_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Cat (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var viewId: Int = 0,
    var imageRes: Int = 0,
    var topMargin: Int = 0,
    var leftMargin: Int = 0,
    var h: Int = 0,
    var w: Int = 0
)