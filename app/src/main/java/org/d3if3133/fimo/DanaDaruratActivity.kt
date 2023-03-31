package org.d3if3133.fimo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        val arrayKategori = arrayOf("Menikah", "Belum Menikah")
        val adapter = ArrayAdapter(this, R.layout.kategori_item, arrayKategori)
        binding.kategori.setAdapter(adapter)
        binding.kategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 1) { // "Menikah" selected
                    multiplier = 6
                } else {
                    multiplier = 3 // reset to default
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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