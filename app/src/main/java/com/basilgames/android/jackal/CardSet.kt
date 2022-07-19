package com.basilgames.android.jackal

import java.util.*

class CardSet {

    //var drawableArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }
    private var drawableArray: List<Int> = List(169){0}

    val cardDeck = mapOf(
        R.drawable.cat_square to 40,
        "Mordoc" to 8.0,
        "Sophie" to 5.5)


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