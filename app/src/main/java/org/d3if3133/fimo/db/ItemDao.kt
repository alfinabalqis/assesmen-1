package org.d3if3133.fimo.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.d3if3133.fimo.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * from item")
    fun getItems(): Flow<List<Item>>

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("DELETE FROM item")
    fun clearData()
}
