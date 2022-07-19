package com.basilgames.android.jackal

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        Log.d("gameActivity", "in game activity!")

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getInt("key")
            Toast.makeText(applicationContext, "in game activity! $value", Toast.LENGTH_LONG).show()
        }




    }
}