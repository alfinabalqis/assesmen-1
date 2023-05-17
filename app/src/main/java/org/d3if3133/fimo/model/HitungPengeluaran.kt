package org.d3if3133.fimo.model

import org.d3if3133.fimo.db.FimoEntity

fun FimoEntity.hitungPengeluaran(): HasilPengeluaran{
    val saldoAwal = saldoAwal
    val pengeluaran = pengeluaran
    val kategori =
        if (saldoAwal > pengeluaran){
            KategoriPengeluaran.HEMAT
        } else if (saldoAwal < pengeluaran){
            KategoriPengeluaran.BOROS
        } else {
            KategoriPengeluaran.BALANCE
        }
    return HasilPengeluaran(kategori)
}
