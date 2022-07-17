package com.basilgames.android.jackal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.basilgames.android.jackal.Tile
import java.util.*


@Dao
interface TileDataAccessObject {

    @Query("SELECT * FROM tile")
    fun getTiles(): List<Tile>


    @Query("SELECT * FROM tile WHERE id=(:id)")
    fun getTile(id: UUID): Tile?

    @Update
    fun updateTile(tile: Tile)

    @Insert
    fun addTile(tile: Tile)
}