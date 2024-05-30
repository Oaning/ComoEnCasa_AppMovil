package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuSemanalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_semanal)

        val botonVolver = findViewById<Button>(R.id.menusemanalBotonVolver)
        botonVolver.setOnClickListener{
            val intentVolver = Intent(this, MainActivity::class.java)
            startActivity(intentVolver)
        }
    }
}