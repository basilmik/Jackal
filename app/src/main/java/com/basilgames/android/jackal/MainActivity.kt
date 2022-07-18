package com.basilgames.android.jackal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basilgames.android.jackal.database.Tile
import com.basilgames.android.jackal.database.TileRepository


private const val TAG = "MainActivity"

var tileIdArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }


class MainActivity : AppCompatActivity() {

    private lateinit var tileField: GridLayout

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button
    private lateinit var deleteButton: Button
    private lateinit var loadButton: Button
    private lateinit var saveButton: Button

    lateinit var tileGrid: TileGrid


    var count = 1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        tableView = findViewById(R.id.table_view)
        addViewButton = findViewById(R.id.addview)
        addGridButton = findViewById(R.id.addgrid)
        deleteButton = findViewById(R.id.deletegrid)
        loadButton = findViewById(R.id.loadgrid)
        saveButton = findViewById(R.id.savegrid)

        tileGrid = TileGrid(this)

        addGridButton.setOnClickListener { // initialising new layout
            /*tileField = GridLayout(this)

            tileField.columnCount = 13
            tileField.rowCount = 13
*/
            val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams()
            gridLayoutParam.height = (1300 * resources.displayMetrics.density).toInt()
            gridLayoutParam.width = (1300 * resources.displayMetrics.density).toInt()

            //tableView.addView(tileField, gridLayoutParam)

            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tableView.addView(tileGrid, gridLayoutParam)

            tileGrid.create()
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
            val db: TileRepository = TileRepository.get()

            val tileList = db.getTiles2()

            Toast.makeText(applicationContext, "db size is : ${tileList.size}", Toast.LENGTH_LONG)
                .show()
            db.deleteTiles()

        }

        loadButton.setOnClickListener {
            val db: TileRepository = TileRepository.get()
            val tileList = db.getTiles2()

            Toast.makeText(applicationContext, "db size is : ${tileList.size}", Toast.LENGTH_LONG)
                .show()

        }

        saveButton.setOnClickListener {
            val db: TileRepository = TileRepository.get()
            db.deleteTiles()

            for (columnIndex in 0..12) {
                for (rowIndex in 0..12) {

                    val tile: Tile = Tile()

                    val tileView = findViewById<TileView>(tileIdArray[columnIndex][rowIndex])

                    tile.imageRes = tileView.getImageRes()
                    tile.isFaceUp = tileView.isFaceUp()

                    tile.row = rowIndex
                    tile.col = columnIndex
                    db.addTile(tile)

                }
            }


        }


    }

}


fun gettileField(i:Int, j: Int): Int
{
    return tileIdArray[i][j]
}