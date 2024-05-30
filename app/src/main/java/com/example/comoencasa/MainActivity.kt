package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarMenu()
    }

    fun iniciarMenu(){
        verFavoritos()
        verPerfil()
        verMenuSemanal()
        verNevera()
    }

    fun verFavoritos(){
        val botonFavoritos = findViewById<ImageButton>(R.id.menuBotonFavoritos)
        botonFavoritos.setOnClickListener{
            val intentoFavoritos = Intent(this, FavoritosActivity::class.java)
            startActivity(intentoFavoritos)
        }
    }

    fun verPerfil(){
        val botonPerfil = findViewById<ImageButton>(R.id.menuBotonPerfil)
        botonPerfil.setOnClickListener{
            val intentPerfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentPerfil)
        }
    }

    fun verMenuSemanal(){
        val botonMenuSemanal = findViewById<ImageButton>(R.id.menuBotonMenuSemanal)
        botonMenuSemanal.setOnClickListener {
            val intentMenuSemanal = Intent(this, MenuSemanalActivity::class.java)
            startActivity(intentMenuSemanal)
        }
    }

    fun verNevera(){
        val botonNevera = findViewById<ImageButton>(R.id.menuBotonNevera)
        botonNevera.setOnClickListener {
            val intentNevera = Intent(this, NeveraActivity::class.java)
            startActivity(intentNevera)
        }
    }


    /*
    al final lo haré de otra forma
    //se coloca el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }*/
}