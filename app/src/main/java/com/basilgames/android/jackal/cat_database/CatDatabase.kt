package com.basilgames.android.jackal.cat_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [ Cat::class ], version = 1)
@TypeConverters(CatTypeConverters::class)
abstract class CatDatabase: RoomDatabase() {

    abstract fun catDao(): CatDataAccessObject

}