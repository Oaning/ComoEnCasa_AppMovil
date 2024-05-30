package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //si el usuario no está registrado y se quiere registrar, se le manda a la página de registro
        val botonRegistrarse = findViewById<Button>(R.id.loginBotonRegistrarse)
        botonRegistrarse.setOnClickListener {
            val intentRegistro = Intent(this, RegistroActivity::class.java)
            startActivity(intentRegistro)
        }

        //si el usuario está registrado y los datos son correctos, accede a la actividad principal
        iniciarSesion()
    }

    fun iniciarSesion(){
        val botonAcceder = findViewById<Button>(R.id.loginBotonAcceder)
        botonAcceder.setOnClickListener {
            val intentAcceso = Intent (this, MainActivity::class.java)
            startActivity(intentAcceso)
        }
    }
}