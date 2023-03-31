package org.d3if3133.fimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
        binding.btnExit.setOnClickListener{ showExitConfirmationDialog() }
        binding.btnBack.setOnClickListener{ backButton() }
    }

    private fun backButton() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {showExitConfirmationDialog()}
    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit Application")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                // Exit the application
                finishAffinity()
            }
            .setNegativeButton("No", null)
            .show()
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