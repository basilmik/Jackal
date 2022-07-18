package com.basilgames.android.jackal.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "tile-database3"

class TileRepository private constructor(context: Context) {

    private val database: TileDatabase = Room.databaseBuilder(
        context.applicationContext,
        TileDatabase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    private val tileDao = database.tileDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTiles(): LiveData<List<Tile>> = tileDao.getTiles()

    fun getTiles2(): List<Tile> = tileDao.getTiles2()

    fun getTile(id: UUID): LiveData<Tile?> = tileDao.getTile(id)

    fun getTile2(i: Int, j: Int): Tile = tileDao.getTile2(i,j)

    fun getTileViewId(i: Int, j: Int): Int
    {
        val tileList: List<Tile> = tileDao.getTiles2()
        var stile: Tile? = null
        for (tile in tileList) {
            if (tile.row == i && tile.col == j)
            stile = tile

        }

        return stile?.viewId ?: -1
    }

    fun getTileViewId2(i: Int, j: Int): Tile?
    {
        val tileList: List<Tile> = tileDao.getTiles2()
        var stile: Tile? = null
        for (tile in tileList) {
            if (tile.row == i && tile.col == j)
                stile = tile

        }

        return stile
    }


    fun updateTile(tile: Tile)
    {
        executor.execute{
            tileDao.updateTile(tile)
        }
    }

    fun deleteTiles()
    {
        executor.execute{
            tileDao.deleteAllTiles()
        }
    }

    fun addTile(tile: Tile)
    {
        executor.execute{
            tileDao.addTile(tile)
        }
    }


    companion object{
        private var INSTANCE: TileRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null)
            {
                INSTANCE = TileRepository(context)
            }
        }

        @JvmStatic fun get(): TileRepository{
            return INSTANCE?:
            throw IllegalStateException("TileRepository must be initialized")
        }

    }

}