package org.d3if3133.fimo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FimoEntity::class], version = 1, exportSchema = false)
abstract class FimoDb: RoomDatabase() {

    abstract val dao: FimoDao

    companion object{
        @Volatile
        private var INSTANCE: FimoDb? = null

        fun getInstance(context: Context): FimoDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FimoDb::class.java,
                        "bmi.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}