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

    private lateinit var addViewButton: ImageButton
    private lateinit var addGridButton: Button
    private lateinit var deleteButton: Button
    private lateinit var closeAboutButton: Button
    private lateinit var saveButton: Button
    private lateinit var textView: ScrollView

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
        closeAboutButton = findViewById(R.id.close_about)

        textView = findViewById(R.id.textView)

        tileGrid = TileGrid(this)
        cats = CatsOnTable()

        if (tileGrid.getSize() > 0 && cats.getSize() > 0)
        {
            Toast.makeText(applicationContext, "${tileGrid.getSize()} ${cats.getSize()}", Toast.LENGTH_LONG).show()
            tileGrid.columnCount = 13
            tileGrid.rowCount = 13
            tileGrid.loadFromDB(sideLen, sideLen)
            tableView.addChild(tileGrid, 0, 0, minwh, minwh)

            cats.loadFromDB(tableView)
        }
        else
        {
            Toast.makeText(applicationContext, "DB is empty", Toast.LENGTH_LONG).show()
            createNewTileGrid()
        }


        closeAboutButton.setOnClickListener {
            textView.visibility = View.INVISIBLE
            closeAboutButton.isClickable = false
        }




        addViewButton.setOnClickListener {
            val imageView = ImageView(this@MainActivity)

            imageView.setImageResource(R.drawable.coin2)

            imageView.id = ImageView.generateViewId()
            val den = resources.displayMetrics.density
            val x: Int = (w/den).toInt() - 200 - (0..4).random() * 10
            val y: Int = (h/den).toInt() - 200 -(0..3).random() * 10
            count++

            addImageView(imageView, x, y, (sideLen/(den*2)).toInt(), (sideLen/(den*2)).toInt())
            cats.addCatToDB(imageView, R.drawable.coin2)

        }


    }

    private fun addShipsToTable()
    {
        val imageView1 = ImageView(this@MainActivity)
        val imageView2 = ImageView(this@MainActivity)
        val imageView3 = ImageView(this@MainActivity)
        val imageView4 = ImageView(this@MainActivity)

        imageView1.setImageResource(R.drawable.ship1)
        imageView1.id = ImageView.generateViewId()
        val den = resources.displayMetrics.density
        var x = (100/den).toInt()
        val y = ((minwh + 100)/den).toInt()
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

    private fun addPiratesToTable()
    {
        val playersList = listOf(
            R.drawable.ship1,
            R.drawable.ship2,
            R.drawable.ship3,
            R.drawable.ship4)

        for (p in 0 until 4)
        for (i in 0 until 3)
        {
            val imageView = ImageView(this@MainActivity)
            imageView.setImageResource(playersList[p])
            imageView.id = ImageView.generateViewId()
            val den = resources.displayMetrics.density
            var x = ((500 + p*100)/den).toInt()
            val y = ((minwh + 100 + i*20)/den).toInt()
            addImageView(imageView, x, y, (sideLen/den).toInt(), (sideLen/den).toInt())
            cats.addCatToDB(imageView, playersList[p])
        }

    }

    override fun onPause() {
        super.onPause()
        tileGrid.saveToDB()
        val den = resources.displayMetrics.density
        cats.saveToDB(tableView, den)

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_game -> {

                createNewTileGrid()
                return true
            }
            R.id.action_rules -> {

                textView.visibility = View.VISIBLE
                closeAboutButton.isClickable = true
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
        tableView.removeAllViews()
        cats.clearDB()
        tileGrid.clearDB()

        tileGrid.columnCount = 13
        tileGrid.rowCount = 13
        tableView.addChild(tileGrid, 0, 0, minwh, minwh)

        tileGrid.createNewGrid(sideLen, sideLen)

        addShipsToTable()
        addPiratesToTable()
    }

}

