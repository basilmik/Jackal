package com.basilgames.android.jackal.cat_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.basilgames.android.jackal.database.Tile

@Dao
interface CatDataAccessObject {
    /*@Query("SELECT * FROM tile")
    fun getTiles(): LiveData<List<Tile>>*/

    @Query("SELECT * FROM cat")
    fun getCats(): List<Cat>

    /*@Query("SELECT * FROM tile WHERE id=(:id)")
    fun getTile(id: UUID): LiveData<Tile?>*/

    @Update
    fun updateCat(cat: Cat)

    @Insert
    fun addCat(cat: Cat)

    @Query("DELETE FROM cat")
    fun deleteAllCats()

}