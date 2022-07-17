package com.basilgames.android.jackal

import android.app.Application
import com.basilgames.android.jackal.database.TileRepository

class JackalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TileRepository.initialize(this)
    }

}