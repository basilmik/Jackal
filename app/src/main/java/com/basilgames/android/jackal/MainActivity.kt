package com.basilgames.android.jackal

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.basilgames.android.jackal.database.TileDatabase


private const val TAG = "MainActivity"

var tileIdArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }

class MainActivity : AppCompatActivity() {

    lateinit var tileField: GridLayout

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button
    private lateinit var deleteButton: Button
    private lateinit var loadButton: Button

    var count = 1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        tableView = findViewById(R.id.field_view)
        addViewButton = findViewById(R.id.addview)
        addGridButton = findViewById(R.id.addgrid)
        deleteButton = findViewById(R.id.deletegrid)
        loadButton = findViewById(R.id.loadgrid)

        addGridButton.setOnClickListener { // initialising new layout
            tileField = GridLayout(this)

            tileField.columnCount = 13
            tileField.rowCount = 13

            val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams()
            gridLayoutParam.height = (1300 * resources.displayMetrics.density).toInt()
            gridLayoutParam.width = (1300 * resources.displayMetrics.density).toInt()

            tableView.addView(tileField, gridLayoutParam)

            create()
        }

        addViewButton.setOnClickListener { // initialising new layout
            val imageView = ImageView(this@MainActivity)

            imageView.setImageResource(R.drawable.cat_square)
            val x: Int = count * 50
            val y: Int = count * 50 + 100
            val den = resources.displayMetrics.density
            val w: Int = (100 * den).toInt()
            val h: Int = (100 * den).toInt()

            tableView.addChild(imageView, x, y, w, h)
            count++
        }

        deleteButton.setOnClickListener {
            val db : TileRepository = TileRepository.get()
            val tileList = db.getTiles()
            //.makeText(applicationContext,"db size is : ${tileList.size}", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext,"db size is : HERE", Toast.LENGTH_LONG).show()
            //db.deleteTiles()
        }

        loadButton.setOnClickListener{
            val db : TileRepository = TileRepository.get()
           // val tileList = db.getTiles()
            val tileListLiveData = db.getTiles2()
            tileListLiveData.observe(this,
                Observer { tiles ->
                    tiles?.let{
                        Toast.makeText(applicationContext,"db size is : ${tiles.size}", Toast.LENGTH_LONG).show()
                    }
                })

            //Toast.makeText(applicationContext,"db size is : ${tileList.size}", Toast.LENGTH_LONG).show()
        }

    }


    private fun create() {
        //var dbSize: Int = 0

        val db: TileRepository = TileRepository.get()
        val tileList = db.getTiles()
        val den = resources.displayMetrics.density
        if (tileList.isEmpty()) // create new grid
        {
            for (columnIndex in 0..12) {
                for (rowIndex in 0..12) {
                    val tileView = TileView(this@MainActivity)
                    tileView.setImageResource(R.drawable.tile_cover)
                    tileView.setImageRes(R.drawable.ball)
                    tileView.id = ImageView.generateViewId()
                    tileIdArray[columnIndex][rowIndex] = tileView.id


                    val row = GridLayout.spec(rowIndex, 1)
                    val column = GridLayout.spec(columnIndex, 1)
                    val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams(row, column)
                    gridLayoutParam.height= (100 * den).toInt()
                    gridLayoutParam.width= (100 * den).toInt()
                    tileField.addView(tileView, gridLayoutParam)


                    val tile = Tile()
                    tile.imageRes = R.drawable.ball
                    tile.isFaceUp = false
                    tile.col = columnIndex
                    tile.row = rowIndex
                    db.addTile(tile)
                }
            }
        }
        else
        {
            for (tile in tileList)
            {
                val row = GridLayout.spec(tile.row, 1)
                val column = GridLayout.spec(tile.col, 1)
                val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams(row, column)
                gridLayoutParam.height= (100 * den).toInt()
                gridLayoutParam.width= (100 * den).toInt()

                val tileView = TileView(this@MainActivity)

                tileView.id = ImageView.generateViewId()
                tileView.setImageRes(tile.imageRes)
                tileView.setFaceUp(tile.isFaceUp)
                if (tileView.isFaceUp())
                    tileView.flipTile()


                tileIdArray[tile.row][tile.col] = tileView.id


                tileField.addView(tileView, gridLayoutParam)

            }
        }




    }

}


fun gettileField(i:Int, j: Int): Int
{
    return tileIdArray[i][j]
}