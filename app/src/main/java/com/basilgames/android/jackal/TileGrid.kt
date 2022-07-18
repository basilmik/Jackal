package com.basilgames.android.jackal

import android.content.Context
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileRepository

class TileGrid(context: Context?) : GridLayout(context!!) {

    fun create(){
        val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles2()

        if (tileList.isEmpty()) {
            // create new grid
            for (columnIndex in 0..12) {
                for (rowIndex in 0..12) {

                    val tileView = TileView(context)

                    tileView.setImageResource(R.drawable.tile_cover)
                    tileView.setImageRes(R.drawable.ball)
                    tileView.id = ImageView.generateViewId()

                    tileIdArray[columnIndex][rowIndex] = tileView.id

                    val den = resources.displayMetrics.density
                    this.addView(tileView, (100 * den).toInt(), (100 * den).toInt())

                    val tile: Tile = Tile()
                    tile.imageRes = R.drawable.ball
                    tile.isFaceUp = false
                    tile.row = rowIndex
                    tile.col = columnIndex
                    db.addTile(tile)

                }
            }

        }
        else
        // upload from db
        {
            Toast.makeText(context.applicationContext,"upload", Toast.LENGTH_LONG).show()
            for (tile in tileList) {

                val tileView = TileView(context)
                tileView.setImageRes(tile.imageRes)
                tileView.setImageResource(R.drawable.tile_cover)
                tileView.setFaceUp(tile.isFaceUp)
                if (tileView.isFaceUp()) {
                    tileView.setImageResource(tile.imageRes)
                }

                tileView.id = ImageView.generateViewId()
                tileIdArray[tile.col][tile.row] = tileView.id

                val row = GridLayout.spec(tile.row, 1)
                val column = GridLayout.spec(tile.col, 1)
                val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams(column, row)
                val den = resources.displayMetrics.density
                gridLayoutParam.height = (100 * den).toInt()
                gridLayoutParam.width = (100 * den).toInt()
                this.addView(tileView, gridLayoutParam)

            }
        }
    }
}