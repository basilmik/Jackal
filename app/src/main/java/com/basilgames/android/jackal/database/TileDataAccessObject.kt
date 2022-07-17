package com.basilgames.android.jackal.database

import androidx.room.Dao
import androidx.room.Query
import com.basilgames.android.jackal.Tile
import java.util.*


@Dao
interface TileDataAccessObject {

    @Query("SELECT * FROM tile")
    fun getTiles(): List<Tile>


    @Query("SELECT * FROM tile WHERE id=(:id)")
    fun getCrime(id: UUID): Tile?

}