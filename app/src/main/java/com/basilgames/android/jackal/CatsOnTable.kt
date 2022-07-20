package com.basilgames.android.jackal

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.basilgames.android.jackal.cat_database.Cat
import com.basilgames.android.jackal.cat_database.CatRepository
import java.security.AccessController.getContext

private const val TAG = "cats"

class CatsOnTable {
    private val db: CatRepository = CatRepository.get()

    fun clearDB()
    {
        db.deleteCats()
        Log.d(TAG, "catsDeleted size^ ${getSize()}")
    }

    fun addCatToDB(catView: ImageView, res: Int)
    {
        val cat = Cat()

        cat.imageRes = res
        cat.viewId = catView.id
        cat.leftMargin = catView.left
        cat.topMargin = catView.top

        cat.h = catView.height
        cat.w = catView.width

        Log.d(TAG, "addCatToDB id ${catView.id} l ${catView.left} t ${catView.top} w ${catView.width} h ${catView.height}")

        db.addCat(cat)

    }



    fun saveToDB(tableView: ZoomableView, den: Float)
    {
        val catList = db.getCats()
        Log.d(TAG, "saveToDB: size ${catList.size}")


        for (cat in catList)
        {
            val catView: ImageView = tableView.findViewById(cat.viewId)

                Log.d(
                    TAG,
                    "\tid ${cat.viewId} l ${catView.left} t ${catView.top} w ${catView.width} h ${catView.height}"
                )

                cat.leftMargin = (catView.left / den).toInt() //  / den
                cat.topMargin = (catView.top / den).toInt()
                cat.h = (catView.height / den).toInt()
                cat.w = (catView.width / den).toInt()
                db.updateCat(cat)

            //Log.d(TAG, "UPDATE   catView.leftMargin ${cat.leftMargin} cat.h ${cat.h}")
        }

        Log.d(TAG, "saveToDB.End")
    }

    fun loadFromDB(tableView: ZoomableView)
    {
        val catList = db.getCats()
        Log.d(TAG, "loadFromDB: size ${catList.size}")

        for (cat in catList)
        {
            //Log.d(TAG, "get view id ${cat.viewId}")
            val catView = ImageView(tableView.context.applicationContext)

            catView.id = ImageView.generateViewId()//cat.viewId
            cat.viewId = catView.id

            catView.left = cat.leftMargin
            catView.top = cat.topMargin
            db.updateCat(cat)

            Log.d(TAG, "load catView.leftMargin ${cat.leftMargin}")
            catView.setImageResource(cat.imageRes)

            tableView.addChild(catView, cat.leftMargin, cat.topMargin, cat.w, cat.h)

            //Log.d(TAG, "LOAD   catView.leftMargin ${cat.leftMargin} cat.h ${cat.h}")
        }

        Log.d(TAG, "loadFromDB.End")
    }

    fun getSize(): Int{
        return  db.getCats().size
    }



}