package org.d3if3133.fimo.db

import android.app.Application

class FimoApplication : Application() {
    val database: ItemRoomDatabase by lazy {
        ItemRoomDatabase.getDatabase(this)
    }
}