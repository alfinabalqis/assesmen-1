package org.d3if3133.fimo

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import org.d3if3133.fimo.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHitung.setOnClickListener{ hitungButton() }
        binding.btnReset.setOnClickListener{ resetButton() }

        val getUsername:String = intent.getStringExtra("getUsername").toString()
        binding.txtUsername.text = getString(R.string.username, getUsername)

        val getSaldo:String = intent.getStringExtra("getHasilFormat").toString()
        binding.txtSaldoAwal.text = getString(R.string.saldo_awal, getSaldo)

        val datepickerEditText = findViewById<TextInputEditText>(R.id.editTanggal)
        datepickerEditText.setOnClickListener { showDatePicker() }

        val arrayKategori = arrayOf("Makanan", "Minuman", "Pakaian", "Pendidikan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayKategori)
        binding.kategori.setAdapter(adapter)

//        binding.fabDanaDarurat.setOnClickListener {
//            val intent = Intent(this, DanaDaruratActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
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

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val currentDate = Calendar.getInstance()
                if (selectedDate.after(currentDate)) {
                    Toast.makeText(this, "Tidak bisa memilih tanggal di masa depan", Toast.LENGTH_SHORT).show()
                } else {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.editTanggal.setText(dateFormat.format(selectedDate.time))
                }
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        // batasi pengguna untuk memilih tanggal sebelum atau pada hari ini
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

    private fun hitungButton() {
        val tanggal = binding.editTanggal.text.toString()
        val kategori = binding.kategori.text.toString()

        if(TextUtils.isEmpty(tanggal) || TextUtils.isEmpty(kategori)){
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_LONG).show()
        }

        if (binding.editDeskripsi.text.toString().isEmpty()){
            binding.editDeskripsi.setText(R.string.no_desc)
        }
        if(binding.editPengeluaran.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.important_data, Toast.LENGTH_LONG).show()
        } else {
            val saldo = intent.getStringExtra("getSaldo").toString().toFloat()
            val pengeluaran = binding.editPengeluaran.text.toString().toFloat()

            val sisaSaldo = saldo - pengeluaran

            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            formatter.maximumFractionDigits = 0
            val sisa = formatter.format(sisaSaldo)
            binding.txtSisaSaldo.text = getString(R.string.sisa_saldo, sisa)

            if (pengeluaran < saldo) {
                binding.tipePengeluaran.text = getString(R.string.surplus)
            } else if(pengeluaran == saldo) {
                binding.tipePengeluaran.text = getString(R.string.balance)
            } else {
                binding.tipePengeluaran.text = getString(R.string.defisit)
            }
        }
    }

    private fun resetButton() {
        // val username = binding.editUsername.text.toString()
        val pengeluaran = binding.editPengeluaran.text.toString()
        val tanggal = binding.editTanggal.text.toString()
        val kategori = binding.kategori.text.toString()

        if(TextUtils.isEmpty(pengeluaran) || TextUtils.isEmpty(tanggal) || TextUtils.isEmpty(kategori)){
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show()
        } else {
            binding.editPengeluaran.text = null
            binding.editDeskripsi.text = null
            binding.txtSisaSaldo.text = null
            binding.tipePengeluaran.text = null
            binding.editTanggal.text = null

            Toast.makeText(this, R.string.hapus_data, Toast.LENGTH_SHORT).show()
        }
    }
}