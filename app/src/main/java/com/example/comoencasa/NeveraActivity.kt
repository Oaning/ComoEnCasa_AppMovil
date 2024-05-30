package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NeveraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nevera)

        val botonVolver = findViewById<Button>(R.id.neveraBotonVolver)
        botonVolver.setOnClickListener{
            val intentVolver = Intent(this, MainActivity::class.java)
            startActivity(intentVolver)
        }
    }
}