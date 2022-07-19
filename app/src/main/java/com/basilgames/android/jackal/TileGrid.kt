package com.basilgames.android.jackal

import android.content.Context
import android.util.Log
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileRepository

private const val TAG = "TileGrid"
private const val TAG2 = "TileGrid2"

class TileGrid(context: Context?) : GridLayout(context!!) {

    private val db: TileRepository = TileRepository.get()
    private val tileList = db.getTiles2()
    private val den = resources.displayMetrics.density

    var isGridSet = false

    // create new grid
    fun createNewGrid(){

        clearDB()
        for (columnIndex in 0..12) {
            for (rowIndex in 0..12) {

                // new tile with params
                val tileView = TileView(context)
                tileView.id = ImageView.generateViewId()
                tileView.setImageResource(R.drawable.tile_cover)
                tileView.setImageRes(R.drawable.ball)
                //tileView.rotation = 90F
                tileView.setFaceUp(false)


               // add into the grid layout
                this.addView(tileView, (100 * den).toInt(), (100 * den).toInt())

                // new line into db
                val tile: Tile = Tile()
                tile.imageRes = R.drawable.ball
                tile.isFaceUp = false
                tile.row = rowIndex
                tile.col = columnIndex
                tile.viewId = tileView.id
                db.addTile(tile)
            }
        }
        isGridSet = true
    }

    fun saveToDB()
    {

        for (columnIndex in 0..12) {
            for (rowIndex in 0..12) {

                val tile2 = db.getTile2(rowIndex , columnIndex)
                val tileView = findViewById<TileView>(tile2.viewId)

                tile2.imageRes = tileView.getImageRes()
                tile2.isFaceUp = tileView.isFaceUp()

                tile2.viewId = tileView.id

                db.updateTile(tile2)
            }
        }

        Toast.makeText(context.applicationContext, "tileList ${tileList.size}", Toast.LENGTH_LONG).show()
    }

    fun loadFromDB()
    {
        for (tile in tileList) {
            Log.d(TAG, "id = ${tile.id}")
            val tileView = TileView(context)
            tileView.setImageRes(tile.imageRes)
            tileView.setImageResource(R.drawable.tile_cover)
            tileView.setFaceUp(tile.isFaceUp)
            if (tileView.isFaceUp()) { // open card
                tileView.setImageResource(tile.imageRes)
            }

            // generating new view id
            tileView.id = ImageView.generateViewId()
            tile.viewId = tileView.id
            db.updateTile(tile)

            val row = spec(tile.row, 1)
            val column = spec(tile.col, 1)
            val gridLayoutParam = LayoutParams(column, row)

            gridLayoutParam.height = (100 * den).toInt()
            gridLayoutParam.width = (100 * den).toInt()
            this.addView(tileView, gridLayoutParam)
        }
        isGridSet = true
    }



    fun clearDB()
    {
        db.deleteTiles()
    }


}