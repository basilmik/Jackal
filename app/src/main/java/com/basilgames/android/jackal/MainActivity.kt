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

            val tileList = db.getTiles2()

            Toast.makeText(applicationContext,"db size is : ${tileList.size}", Toast.LENGTH_LONG).show()
            db.deleteTiles()

            /*val tileListLiveData = db.getTiles()
            tileListLiveData.observe(this,
                Observer { tiles ->
                    tiles?.let{
                        //Toast.makeText(applicationContext,"db size is : ${tiles.size}", Toast.LENGTH_LONG).show()
                    }
                })*/


        }

        loadButton.setOnClickListener{
            val db : TileRepository = TileRepository.get()
            val tileList = db.getTiles2()

            Toast.makeText(applicationContext,"db size is : ${tileList.size}", Toast.LENGTH_LONG).show()

            /*val tileListLiveData = db.getTiles()
            tileListLiveData.observe(this,
            Observer { tiles ->
                tiles?.let{
                    Toast.makeText(applicationContext,"db size is : ${tiles.size}", Toast.LENGTH_LONG).show()
                }
            })*/
        }

    }


    private fun create() {
        val db : TileRepository = TileRepository.get()

        for (columnIndex in 0..12) {
            for (rowIndex in 0..12) {
                val row = GridLayout.spec(rowIndex, 1)
                val column = GridLayout.spec(columnIndex, 1)

                val tileView = TileView(this@MainActivity)

                tileView.setImageResource(R.drawable.tile_cover)
                tileView.setImageRes(R.drawable.ball)

                tileView.id = ImageView.generateViewId()

                tileIdArray[columnIndex][rowIndex] = tileView.id
                val den = resources.displayMetrics.density
                tileField.addView( tileView, (100 * den).toInt(), (100 * den).toInt() )
                var tile: Tile = Tile()

                tile.imageRes = R.drawable.ball
                tile.isFaceUp = false
                db.addTile(tile)
                //Log.d(TAG, "id = ${tile.id}")

            }
        }

    }

}


fun gettileField(i:Int, j: Int): Int
{
    return tileIdArray[i][j]
}