package com.basilgames.android.jackal

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView

class TileView: androidx.appcompat.widget.AppCompatImageView {
    private var imageRes: Int = 0
    private var faceUp: Boolean = false

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super( context!!, attrs, defStyle ) { }


    override fun onDraw(canvas: Canvas) {
        /*val clipPath = Path()
        val rect = RectF(0F, 0F, this.width.toFloat(), this.height.toFloat() )
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)*/
        super.onDraw(canvas)
    }

    fun setImageRes(_imageRes: Int)
    {
        imageRes = _imageRes
    }

    fun setFaceUp(_faceUp: Boolean)
    {
        faceUp = _faceUp
    }

    fun getImageRes(): Int {
        return imageRes
    }

    public fun isFaceUp():Boolean
    {
        return faceUp
    }

    fun flipTile()
    {
        faceUp = !faceUp
        if (faceUp) setImageResource(imageRes)
        else
            setImageResource(R.drawable.tile_cover)
    }


}