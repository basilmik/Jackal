package com.basilgames.android.jackal

import android.content.Context
import java.lang.IllegalStateException

class TileRepository private constructor(context: Context) {

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