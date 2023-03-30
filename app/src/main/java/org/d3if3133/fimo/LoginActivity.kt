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