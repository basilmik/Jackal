package com.basilgames.android.jackal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Toast.makeText(applicationContext, "in game activity!", Toast.LENGTH_LONG).show()
        Log.d("gameActivity", "in game activity!")
    }
}