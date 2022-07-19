package com.basilgames.android.jackal

import java.util.*

class CardSet {

    //var drawableArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }
    private var drawableArray: List<Int> = List(169){0}

    val cardDeck = mapOf(
        R.drawable.field to 40,

        R.drawable.arrow0 to 2,
        R.drawable.arrow1 to 3,
        R.drawable.arrow2 to 3,
        R.drawable.arrow3 to 3,
        R.drawable.arrow4 to 3,
        R.drawable.arrow5 to 3,
        R.drawable.arrow6 to 3,

        R.drawable.balloon to 2,
        R.drawable.barrel to 4,
        R.drawable.bones to 1,
        R.drawable.camp1 to 2,
        R.drawable.camp2 to 1,

        R.drawable.cannon to 2,

        R.drawable.chest to 1,

        R.drawable.crocodile to 4,
        R.drawable.hourse to 2,
        R.drawable.plane to 1,

        //R.drawable.sea to 1,

        //R.drawable.ship1 to 1,
        //R.drawable.ship2 to 1,
        //R.drawable.ship3 to 1,
        //R.drawable.ship4 to 1,


        R.drawable.stuck2 to 5,
        R.drawable.stuck3 to 4,
        R.drawable.stuck4 to 2,
        R.drawable.stuck5 to 1,

        R.drawable.trap to 3,
        R.drawable.x2 to 6



        //R.drawable.cover to 1,
        )


    fun mixCardSet()
    {

        for (i in 0..12) {
            for (j in 0..12) {

                drawableArray[i*13+j]
                Collections.shuffle(drawableArray);


            }
        }
    }
}