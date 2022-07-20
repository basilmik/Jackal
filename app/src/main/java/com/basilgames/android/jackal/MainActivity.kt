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

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button
    private lateinit var deleteButton: Button
    //private lateinit var loadButton: Button
    private lateinit var saveButton: Button
    private lateinit var textView: TextView

    lateinit var tileGrid: TileGrid
    lateinit var cats: CatsOnTable

    var count = 1
    var minwh = 0
    var sideLen = 0
    var w = 0
    var h = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        h = applicationContext.resources.displayMetrics.heightPixels
        w = applicationContext.resources.displayMetrics.widthPixels
        minwh = min(h, w)
        sideLen = minwh / 13

        tableView = findViewById(R.id.table_view)
        addViewButton = findViewById(R.id.addview)
        //addGridButton = findViewById(R.id.addgrid)
        //deleteButton = findViewById(R.id.deletegrid)
        //loadButton = findViewById(R.id.loadgrid)
        textView = findViewById(R.id.textView)
        //saveButton = findViewById(R.id.savegrid)


        //tileGrid.clearDB()
        //cats.clearDB()
        /*Toast.makeText(
            applicationContext,
            " sea ${tileGrid.getSize()}", Toast.LENGTH_LONG
        ).show()*/
        tileGrid = TileGrid(this)
        cats = CatsOnTable()

        if (tileGrid.getSize() > 0)
        {
            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tileGrid.loadFromDB(sideLen, sideLen)
            tableView.addChild(tileGrid, 0, 0, minwh, minwh)

            //if (cats.getSize() > 0)
                cats.loadFromDB(tableView)
        }
        else
        {
            tileGrid.removeAllViews()
            tableView.removeView(tileGrid)

            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tableView.addChild(tileGrid, 0, 0, minwh, minwh)

            tileGrid.createNewGrid(sideLen, sideLen)
            cats.clearDB()
            addShipsToTable()
        }


        /*addGridButton.setOnClickListener {
            createNewTileGrid()
        }*/

       /* deleteButton.setOnClickListener {
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


        /*saveButton.setOnClickListener {
            tileGrid.saveToDB()
            val den = resources.displayMetrics.density
            cats.saveToDB(tableView, den)
        }*/



        addViewButton.setOnClickListener {
            val imageView = ImageView(this@MainActivity)

            imageView.setImageResource(R.drawable.cat_square)

            imageView.id = ImageView.generateViewId()

            val x: Int = 1 * 25
            val y: Int = 1 * 25 + 50
            //count++

            addImageView(imageView, x, y, 10, 10)
            cats.addCatToDB(imageView, R.drawable.cat_square)

        }


    }

    private fun addShipsToTable()
    {
        val imageView1 = ImageView(this@MainActivity)
        val imageView2 = ImageView(this@MainActivity)
        val imageView3 = ImageView(this@MainActivity)
        val imageView4 = ImageView(this@MainActivity)
        var x = 0
        var y = 0
        imageView1.setImageResource(R.drawable.ship1)
        imageView1.id = ImageView.generateViewId()
        val den = resources.displayMetrics.density
        x = (100/den).toInt()
        y = ((minwh + 100)/den).toInt()

        addImageView(imageView1, x, y, (sideLen/den).toInt(), (sideLen/den).toInt())
        cats.addCatToDB(imageView1, R.drawable.ship1)


        imageView2.setImageResource(R.drawable.ship2)
        imageView2.id = ImageView.generateViewId()
        x = (200/den).toInt()
        addImageView(imageView2, x, y, (sideLen/den).toInt(), (sideLen/den).toInt())
        cats.addCatToDB(imageView2, R.drawable.ship2)

        imageView3.setImageResource(R.drawable.ship3)
        imageView3.id = ImageView.generateViewId()
        x = (300/den).toInt()
        addImageView(imageView3, x, y, (sideLen/den).toInt(), (sideLen/den).toInt())
        cats.addCatToDB(imageView3, R.drawable.ship3)

        imageView4.setImageResource(R.drawable.ship4)
        imageView4.id = ImageView.generateViewId()
        x = (400/den).toInt()
        addImageView(imageView4, x, y, (sideLen/den).toInt(), (sideLen/den).toInt())
        cats.addCatToDB(imageView4, R.drawable.ship4)

    }

    override fun onPause() {
        if (tileGrid.getSize() != 0) tileGrid.saveToDB()
        val den = resources.displayMetrics.density
        if (cats.getSize() != 0) cats.saveToDB(tableView, den)
        super.onPause()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_game -> {
                textView.text = "Restart"
                createNewTileGrid()
                return true
            }
            R.id.action_rules -> {
                textView.text = "Rules"
                return true
            }
            R.id.action_delete -> {
                tileGrid.clearDB()
                cats.clearDB()
                return true
            }
            R.id.action_save -> {
                tileGrid.saveToDB()
                val den = resources.displayMetrics.density
                cats.saveToDB(tableView, den)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun addImageView(imageView: ImageView, x: Int, y: Int, _w: Int, _h: Int)
    {
        tableView.addChild(imageView, x, y, _w, _h)
    }

    private fun createNewTileGrid()
    {
        tileGrid.removeAllViews()
        //tableView.removeView(tileGrid)
        tableView.removeAllViews()
        tileGrid.columnCount = 13
        tileGrid.rowCount = 13
        tableView.addChild(tileGrid, 0, 0, minwh, minwh)

        tileGrid.createNewGrid(sideLen, sideLen)
        cats.clearDB()
        addShipsToTable()
    }

}

