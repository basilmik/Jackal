package com.basilgames.android.jackal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Integer.min
import java.security.AccessController.getContext


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

         val h = applicationContext.resources.displayMetrics.heightPixels
         val w = applicationContext.resources.displayMetrics.widthPixels
         val minwh = min(h, w)
         val sideLen = minwh/13
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

                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addChild(tileGrid, 0, 0, minwh, minwh)

                tileGrid.createNewGrid(sideLen, sideLen)
                cats.clearDB()

            }
        }

        deleteButton.setOnClickListener {
            tileGrid.clearDB()
            cats.clearDB()
        }


        loadButton.setOnClickListener {
            if (!tileGrid.isGridSet) {
                /*val gridLayoutParam: GridLayout.LayoutParams = GridLayout.LayoutParams()
                gridLayoutParam.height = (h * resources.displayMetrics.density).toInt()
                gridLayoutParam.width = (w * resources.displayMetrics.density).toInt()

                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                //tableView.addView(tileGrid, gridLayoutParam)
                tableView.addChild(tileGrid, 0,0, w, h)*/


                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addChild(tileGrid, 0, 0, minwh, minwh)

                tileGrid.loadFromDB(sideLen, sideLen)
                //tileGrid.top = (h-w)/2
                cats.loadFromDB(tableView)
            }
        }



        saveButton.setOnClickListener {
            tileGrid.saveToDB()
            val den = resources.displayMetrics.density
            cats.saveToDB(tableView, den)
        }



        addViewButton.setOnClickListener {
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
        tableView.addChild(imageView, x, y, _w, _h)
    }


}

