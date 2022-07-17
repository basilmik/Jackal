package com.basilgames.android.jackal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


var tileIdArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }

class MainActivity : AppCompatActivity() {

    lateinit var tileField: GridLayout

    private lateinit var tableView: ZoomableView

    private lateinit var addViewButton: Button
    private lateinit var addGridButton: Button

    var count = 1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        tableView = findViewById(R.id.field_view)
        addViewButton = findViewById(R.id.addview)
        addGridButton = findViewById(R.id.addgrid)

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

    }


    private fun create() {
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

            }
        }

    }


}


fun gettileField(i:Int, j: Int): Int
{
    return tileIdArray[i][j]
}