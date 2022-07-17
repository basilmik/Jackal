package com.basilgames.android.jackal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Tile (
        @PrimaryKey val id: UUID = UUID.randomUUID(),
        var col: Int = 0,
        var row: Int = 0,
        var isFaceUp: Boolean = false,
        var imageRes: Int = 0
)