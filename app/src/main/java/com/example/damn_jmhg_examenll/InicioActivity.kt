package com.example.damn_jmhg_examenll

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnUsuarios = findViewById<Button>(R.id.btnUsers)
        val btnCreditos = findViewById<Button>(R.id.btnCreditos)
        val btnCerrar = findViewById<Button>(R.id.btnCerrar)

        btnUsuarios.setOnClickListener {
            val intentUsuarios = Intent(this, MainActivity::class.java)
            startActivity(intentUsuarios)
        }

        btnCreditos.setOnClickListener {
            val intentCreditos = Intent(this, CreditosActivity::class.java)
            startActivity(intentCreditos)
        }

        btnCerrar.setOnClickListener {
            finish()
        }
    }
}