package org.d3if3133.fimo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String,
    val itemPrice: Double,
    var tanggal: Long = System.currentTimeMillis(),
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//    NumberFormat.getCurrencyInstance().format(itemPrice)