package com.basilgames.android.jackal.cat_database

import android.content.Context
import androidx.room.Room
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileRepository
import java.lang.IllegalStateException
import java.util.concurrent.Executors


private const val CAT_DATABASE_NAME = "cat-database"

class CatRepository private constructor(context: Context){

    private val database: CatDatabase = Room.databaseBuilder(
        context.applicationContext,
        CatDatabase::class.java,
        CAT_DATABASE_NAME
    ).allowMainThreadQueries().build()

    private val catDao = database.catDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCats(): List<Cat> = catDao.getCats()





    companion object{
        private var INSTANCE: CatRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null)
            {
                INSTANCE = CatRepository(context)
            }
        }

        @JvmStatic fun get(): CatRepository {
            return INSTANCE?:
            throw IllegalStateException("CatRepository must be initialized")
        }

    }


}