package com.basilgames.android.jackal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.basilgames.android.jackal.Tile
import java.util.*


@Dao
interface TileDataAccessObject {

    @Query("SELECT * FROM tile")
    fun getTiles(): LiveData<List<Tile>>


    @Query("SELECT * FROM tile WHERE id=(:id)")
    fun getTile(id: UUID): LiveData<Tile?>

    @Update
    fun updateTile(tile: Tile)

    @Insert
    fun addTile(tile: Tile)

    @Query("DELETE FROM tile")
    fun deleteAllTiles()
}