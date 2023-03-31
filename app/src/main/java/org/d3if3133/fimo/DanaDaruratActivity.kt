package org.d3if3133.fimo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if3133.fimo.databinding.ActivityDanaDaruratBinding
import java.text.NumberFormat
import java.util.*

class DanaDaruratActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDanaDaruratBinding
    private var multiplier = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_dana_darurat)
        binding = ActivityDanaDaruratBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHitung.setOnClickListener{ hitungButton() }
        binding.btnReset.setOnClickListener{ resetButton() }
    }

    private fun resetButton() {
        binding.editPengeluaran.text = null
        binding.totalDana.text = ""
    }

    private fun hitungButton() {
        val gaji = binding.editPengeluaran.text.toString().toInt()
        val totalDanaDarurat = gaji * multiplier
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        val sisa = formatter.format(totalDanaDarurat)
        binding.totalDana.setText(sisa)
    }
}