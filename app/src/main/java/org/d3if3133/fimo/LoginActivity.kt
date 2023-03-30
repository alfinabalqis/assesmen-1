package org.d3if3133.fimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import org.d3if3133.fimo.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{ loginButton()}

//        binding.editSaldo.addTextChangedListener(object : TextWatcher{
//            private var current = ""
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                if(s.toString() != current){
//                    binding.editSaldo.removeTextChangedListener(this)
//
//                    val cleanString = s.toString().replace("[Rp ,.\\s]".toRegex(), "")
//                    val parsed = cleanString.toLong()
//                    val formatted = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(parsed)
//
//                    current = formatted
//                    binding.editSaldo.setText(formatted)
//                    binding.editSaldo.setSelection(formatted.length)
//                    binding.editSaldo.addTextChangedListener(this)
//                }
//            }
//        })


        // val editSaldo = findViewById<TextInputEditText>(R.id.editSaldo)
        //
        //        // Set locale ke Indonesia untuk format mata uang rupiah
        //        val localeID = Locale("in", "ID")
        //
        //        // Buat objek NumberFormat untuk format mata uang rupiah
        //        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        //
        //        editSaldo.addTextChangedListener(object : TextWatcher {
        //            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        //
        //            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        //
        //            override fun afterTextChanged(s: Editable?) {
        //                if (s.toString().isNotEmpty()) {
        //                    // Konversi input ke dalam bentuk angka
        //                    val saldo = s.toString().toLong()
        //
        //                    // Format angka menjadi format mata uang rupiah
        //                    val saldoFormatted = formatRupiah.format(saldo)
        //
        //                    // Set text pada TextInputEditText dengan format mata uang rupiah
        //                    editSaldo.removeTextChangedListener(this)
        //                    editSaldo.setText(saldoFormatted)
        //                    editSaldo.setSelection(saldoFormatted.length)
        //                    editSaldo.addTextChangedListener(this)
        //                }
    }

    private fun loginButton() {
        val username = binding.editUsername.text.toString()
        val saldoAwal = binding.editSaldo.text.toString()

        // val pass = binding.editPass.text.toString()
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(saldoAwal)){
            Toast.makeText(this, R.string.invalid, Toast.LENGTH_LONG).show()
            return
        } else {
            Toast.makeText(this, R.string.sukses, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("getUsername", username)
            intent.putExtra("getSaldo", saldoAwal)
            startActivity(intent)
            finish()
        }
    }
}