package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val botonAcceder = findViewById<Button>(R.id.registroBoton)
        botonAcceder.setOnClickListener {
            val intentAcceso = Intent (this, MainActivity::class.java)
            startActivity(intentAcceso)
        }
    }
}