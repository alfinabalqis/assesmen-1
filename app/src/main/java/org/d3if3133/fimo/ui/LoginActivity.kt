package org.d3if3133.fimo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.d3if3133.fimo.MainActivity
import org.d3if3133.fimo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener { loginButton() }
    }

    private fun loginButton() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}