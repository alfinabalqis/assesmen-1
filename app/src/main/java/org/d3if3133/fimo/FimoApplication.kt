package org.d3if3133.fimo

import android.app.Application
import org.d3if3133.fimo.db.ItemRoomDatabase

class FimoApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}