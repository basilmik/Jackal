package com.basilgames.android.jackal

import android.app.Application

class JackalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TileRepository.initialize(this)
    }

}