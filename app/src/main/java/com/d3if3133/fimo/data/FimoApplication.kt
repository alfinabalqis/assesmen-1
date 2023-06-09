package com.d3if3133.fimo.data

import android.app.Application


class FimoApplication : Application() {
    val database: ItemRoomDatabase by lazy {
        ItemRoomDatabase.getDatabase(this)
    }
}
