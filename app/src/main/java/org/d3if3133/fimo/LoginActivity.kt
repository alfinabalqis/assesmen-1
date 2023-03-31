package org.d3if3133.fimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.d3if3133.fimo.databinding.ActivityLoginBinding
import java.text.NumberFormat
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{ loginButton()}
    }

    private fun loginButton() {
        val username = binding.editUsername.text.toString()
        val saldoAwal = binding.editSaldo.text.toString()

        val inputan = binding.editSaldo
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        formatter.maximumFractionDigits = 0
        val hasilFormat = formatter.format(inputan.text.toString().toDouble())

        // val pass = binding.editPass.text.toString()
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(saldoAwal)){
            Toast.makeText(this, R.string.invalid, Toast.LENGTH_LONG).show()
            return
        } else {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Yakin saldo anda $hasilFormat ?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(this, R.string.sukses, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("getUsername", username)
                    intent.putExtra("getSaldo", saldoAwal)
                    intent.putExtra("getHasilFormat", hasilFormat)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}