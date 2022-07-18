package com.basilgames.android.jackal.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "tile-database3"
private const val TAG = "TileRep"

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

    fun getTileViewId(r: Int, c: Int): Int
    {
        val tileList: List<Tile> = tileDao.getTiles2()
        var stile: Tile? = null
        for (tile in tileList) {
            Log.d(TAG, "${tile.col} ${tile.row}  $r $c")
            if (tile.row == r && tile.col == c)
            stile = tile

        }

        return stile?.viewId ?: -1
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