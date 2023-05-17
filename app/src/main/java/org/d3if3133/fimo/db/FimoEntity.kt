package org.d3if3133.fimo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fimo")
data class FimoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var saldoAwal: Float,
    var pengeluaran: Float,
)