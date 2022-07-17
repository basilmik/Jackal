package com.basilgames.android.jackal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.basilgames.android.jackal.Tile

@Database(entities = [ Tile::class ], version = 2)
@TypeConverters(TileTypeConverters::class)
abstract class TileDatabase: RoomDatabase() {

    abstract fun tileDao(): TileDataAccessObject

}