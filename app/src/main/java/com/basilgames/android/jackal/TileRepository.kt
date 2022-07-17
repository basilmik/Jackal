package com.basilgames.android.jackal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.basilgames.android.jackal.database.TileDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "tile-database"

class TileRepository private constructor(context: Context) {

    private val database: TileDatabase = Room.databaseBuilder(
        context.applicationContext,
        TileDatabase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    private val tileDao = database.tileDao()
    private val executor = Executors.newSingleThreadExecutor()


    fun getTiles(): List<Tile> = tileDao.getTiles()

    fun getTiles2(): LiveData<List<Tile>> = tileDao.getTiles2()

    fun getTile(id: UUID): LiveData<Tile?> = tileDao.getTile(id)


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

        fun get(): TileRepository{
            return INSTANCE?:
            throw IllegalStateException("TileRepository must be initialized")
        }

    }

}