package com.basilgames.android.jackal

import android.content.Context
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileRepository

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
                // set resource as cover
                tileView.setImageResource(R.drawable.tile_cover)
                // set card face
                tileView.setImageRes(R.drawable.ball)
                // card looks down
                tileView.setFaceUp(false)

                // so it can be accessed by id
                tileIdArray[columnIndex][rowIndex] = tileView.id

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
        clearDB()
        for (columnIndex in 0..12) {
            for (rowIndex in 0..12) {

                val tileView = findViewById<TileView>(tileIdArray[columnIndex][rowIndex])
                val tile = Tile()

                tile.imageRes = tileView.getImageRes()
                tile.isFaceUp = tileView.isFaceUp()
                tile.row = rowIndex
                tile.col = columnIndex
                tile.viewId = tileView.id
                db.addTile(tile)
            }
        }
    }

    fun loadFromDB()
    {
        for (tile in tileList) {

            val tileView = TileView(context)
            tileView.setImageRes(tile.imageRes)
            tileView.setImageResource(R.drawable.tile_cover)
            tileView.setFaceUp(tile.isFaceUp)
            if (tileView.isFaceUp()) { // open card
                tileView.setImageResource(tile.imageRes)
            }


            tileView.id = ImageView.generateViewId()

            // generating new view id
            tileIdArray[tile.col][tile.row] = tileView.id
            tile.viewId = tileView.id

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