package com.basilgames.android.jackal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "MainActivity"

//var tileIdArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }


class MainActivity : AppCompatActivity() {

    private lateinit var tileField: GridLayout

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button
    private lateinit var deleteButton: Button
    private lateinit var loadButton: Button
    private lateinit var saveButton: Button

    lateinit var tileGrid: TileGrid
    lateinit var cats: CatsOnTable



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
        cats = CatsOnTable()



        addGridButton.setOnClickListener { // initialising new layout
            if (!tileGrid.isGridSet) {
                val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams()
                gridLayoutParam.height = (1300 * resources.displayMetrics.density).toInt()
                gridLayoutParam.width = (1300 * resources.displayMetrics.density).toInt()
                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addView(tileGrid, gridLayoutParam)


                tileGrid.createNewGrid()
            }
        }

        deleteButton.setOnClickListener {
            tileGrid.clearDB()
            cats.clearDB()
        }


        loadButton.setOnClickListener {
            if (!tileGrid.isGridSet) {
                val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams()
                gridLayoutParam.height = (1300 * resources.displayMetrics.density).toInt()
                gridLayoutParam.width = (1300 * resources.displayMetrics.density).toInt()

                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addView(tileGrid, gridLayoutParam)

                tileGrid.loadFromDB()
                cats.loadFromDB(tableView)
            }
        }



        saveButton.setOnClickListener {
            tileGrid.saveToDB()
            val den = resources.displayMetrics.density
            cats.saveToDB(tableView, den)
        }



        addViewButton.setOnClickListener { // initialising new layout
            val imageView = ImageView(this@MainActivity)

            imageView.setImageResource(R.drawable.cat_square)

            imageView.id = ImageView.generateViewId()

            val x: Int = count * 50
            val y: Int = count * 50 + 100
            count++

            addImageView(imageView, x, y, 100, 100)
            cats.addCatToDB(imageView, R.drawable.cat_square)


        }


    }


    fun addImageView(imageView: ImageView, x: Int, y: Int, _w: Int, _h: Int)
    {
        //val den = resources.displayMetrics.density
        //val w: Int = (_w * den).toInt()
        //val h: Int = (_h * den).toInt()

        tableView.addChild(imageView, x, y, _w, _h)
    }


}

