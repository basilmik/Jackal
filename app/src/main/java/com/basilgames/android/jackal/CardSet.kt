package com.basilgames.android.jackal

import android.util.Log
import java.util.*

private const val TAG = "card"

public class CardSet {

    //var drawableArray: Array<Array<Int>> = Array(13) { Array(13) { _ -> 0 } }
    var drawableList:  MutableList<Int> = MutableList(117) { _ -> 0 }
    //var drawableList:  MutableList<Int> = MutableList(5) { _ -> 0 }


    val cardDeck = mapOf(
        R.drawable.field to 40,

        R.drawable.arrow0 to 3,
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

        R.drawable.chest1 to 5,
        R.drawable.chest2 to 5,
        R.drawable.chest3 to 3,
        R.drawable.chest4 to 2,
        R.drawable.chest5 to 1,

        R.drawable.crocodile to 4,
        R.drawable.hourse to 2,
        R.drawable.plane to 1,


        R.drawable.stuck2 to 5,
        R.drawable.stuck3 to 4,
        R.drawable.stuck4 to 2,
        R.drawable.stuck5 to 1,

        R.drawable.trap to 3,
        R.drawable.x2 to 6


        /*R.drawable.sea to 52,

        R.drawable.ship1 to 1,
        R.drawable.ship2 to 1,
        R.drawable.ship3 to 1,
        R.drawable.ship4 to 1*/
        //R.drawable.cover to 1,

        )


    fun mixCardSet()
    {
        var offset: Int = 0
        for (card in cardDeck)
        {
           // Log.d(TAG, "card.value = ${card.value}")
            for (i in offset until card.value+offset)
            {
                Log.d(TAG, "i = ${i}")
                drawableList[i] = card.key
                Log.d(TAG, " drawable = ${drawableList[i]}")
            }

            offset += card.value
            //Log.d(TAG, "\toffset = ${offset}")

        }
        //Log.d(TAG, "\tcardDeck.size = ${cardDeck.size}")
        //Log.d(TAG, "\tdrawableList.size = ${drawableList.size}")

        drawableList.shuffle()

        for (i in 0..116)
        {
            //Log.d(TAG, " $i drawable = ${drawableList[i]}")
        }

        /*for (i in 0..12) {
            for (j in 0..12) {

                //drawableArray[i*13+j]
                Collections.shuffle(drawableList);


            }
        }*/
    }
    var takenCount = 0

    fun getNewCard(): Int
    {
        val ret: Int = drawableList[takenCount]
        takenCount += 1
        return ret
    }



}