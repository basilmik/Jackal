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

   // private val db: TileRepository = TileRepository.get()
    //private val tileList = db.getTiles2()
    private val den = resources.displayMetrics.density

    private val N = 13
    var isGridSet = false

    // create new grid
    fun createNewGrid(_h: Int, _w: Int){

        val db: TileRepository = TileRepository.get()
        clearDB()
        val cardSet = CardSet()
        cardSet.mixCardSet()

        for (columnIndex in 0 until N) {
            for (rowIndex in 0 until N) {

                // new tile with params
                val tileView = TileView(context)
                tileView.id = ImageView.generateViewId()
                tileView.setImageResource(R.drawable.cover)

                var newImageRes = 0

                if ((columnIndex == 1 && rowIndex == 1 || columnIndex == 1 && rowIndex == N-2
                            || columnIndex == N-2 && rowIndex == 1 || columnIndex == N-2 && rowIndex == N-2)
                    || (columnIndex == 0 || columnIndex == N-1 || rowIndex == 0 || rowIndex == N-1))
                {
                    newImageRes = R.drawable.sea
                    tileView.setImageRes(newImageRes)
                    tileView.setImageResource(R.drawable.sea)
                    tileView.flipTile()
                    tileView.rotation = 90F*(0..4).random()
                }
                else {
                    newImageRes = cardSet.getNewCard()
                    tileView.setImageRes(newImageRes)
                    tileView.setFaceUp(false)
                    tileView.rotation = 90F*(0..6).random()*(0..7).random()
                }

                // add into the grid layout
                this.addView(tileView, _w, _h)

                Log.d("cats", "addChild h:${tileView.height} w:${tileView.width}")
                // new line into db
                val tile: Tile = Tile()
                tile.imageRes = newImageRes
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
        val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles2()

        for (columnIndex in 0..12) {
            for (rowIndex in 0..12) {

                val tile2 = db.getTile2(rowIndex, columnIndex)
                val tileView = findViewById<TileView>(tile2.viewId)

                tile2.imageRes = tileView.getImageRes()
                tile2.isFaceUp = tileView.isFaceUp()

                tile2.viewId = tileView.id

                db.updateTile(tile2)
            }
        }

        //Toast.makeText(context.applicationContext, "tileList ${tileList.size}", Toast.LENGTH_LONG).show()
    }

    fun loadFromDB(h: Int, w: Int)
    {   val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles2()
        for (tile in tileList) {
            Log.d(TAG, "id = ${tile.id}")
            val tileView = TileView(context)
            tileView.setImageRes(tile.imageRes)
            tileView.setImageResource(R.drawable.cover)
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
            this.addView(tileView, w, h)
        }
        isGridSet = true
    }


    fun getSize(): Int {
        val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles2()
        return tileList.size
    }

    fun clearDB()
    {
        val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles2()
        db.deleteTiles()
    }


}