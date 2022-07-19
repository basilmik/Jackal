package com.basilgames.android.jackal


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.lang.Integer.min


private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private lateinit var tileField: GridLayout

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button
    //private lateinit var deleteButton: Button
    //private lateinit var loadButton: Button
    private lateinit var saveButton: Button
    private lateinit var textView: TextView

    lateinit var tileGrid: TileGrid
    lateinit var cats: CatsOnTable

    var count = 1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val h = applicationContext.resources.displayMetrics.heightPixels
        val w = applicationContext.resources.displayMetrics.widthPixels
        val minwh = min(h, w)
        val sideLen = minwh/13

        tableView = findViewById(R.id.table_view)
        addViewButton = findViewById(R.id.addview)
        addGridButton = findViewById(R.id.addgrid)
        //deleteButton = findViewById(R.id.deletegrid)
        //loadButton = findViewById(R.id.loadgrid)
        textView = findViewById(R.id.textView)
        saveButton = findViewById(R.id.savegrid)

        tileGrid = TileGrid(this)
        cats = CatsOnTable()
        //tileGrid.clearDB()
        //cats.clearDB()
        /*Toast.makeText(
            applicationContext,
            " sea ${tileGrid.getSize()}", Toast.LENGTH_LONG
        ).show()*/

        if (tileGrid.getSize() > 0)
        {
            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tableView.addChild(tileGrid, 0, 0, minwh, minwh)

            tileGrid.loadFromDB(sideLen, sideLen)
            //tileGrid.top = (h-w)/2

            if (cats.getSize() > 0)
            cats.loadFromDB(tableView)
        }else
        {
            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tableView.addChild(tileGrid, 0, 0, minwh, minwh)

            tileGrid.createNewGrid(sideLen, sideLen)
            cats.clearDB()
        }


        addGridButton.setOnClickListener { // initialising new layout
            if (!tileGrid.isGridSet) {
                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addChild(tileGrid, 0, 0, minwh, minwh)

                tileGrid.createNewGrid(sideLen, sideLen)
                cats.clearDB()
            }
        }

        /*deleteButton.setOnClickListener {
            tileGrid.clearDB()
            cats.clearDB()
        }*/



        /*loadButton.setOnClickListener {
            if (!tileGrid.isGridSet) {

                tileGrid.columnCount = 13
                tileGrid.rowCount = 13
                tableView.addChild(tileGrid, 0, 0, minwh, minwh)

                tileGrid.loadFromDB(sideLen, sideLen)
                //tileGrid.top = (h-w)/2
                cats.loadFromDB(tableView)
            }
        }*/



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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_game -> {
                textView.text = "New game!"
                return true
            }
            R.id.action_rules -> {
                textView.text = "Rules"
                return true
            }
            R.id.action_about -> {
                textView.text = "About"
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addImageView(imageView: ImageView, x: Int, y: Int, _w: Int, _h: Int)
    {
        tableView.addChild(imageView, x, y, _w, _h)
    }


}

