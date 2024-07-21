package com.example.comoencasa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
/*
 public class BaseMenu : AppCompatActivity(){
     public fun iniciarMenu(){
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Asigno el botón inicio
        val botonInicio = findViewById<ImageButton>(R.id.menuBotonInicio)
        botonInicio.setOnClickListener{

        }

        //asigno el botón favoritos
        val botonFavoritos = findViewById<ImageButton>(R.id.menuBotonFavoritos)
        botonFavoritos.setOnClickListener{

        }

        //asigno el botón menú semanal
        val botonMenuSemanal = findViewById<ImageButton>(R.id.menuBotonMenuSemanal)
        botonMenuSemanal.setOnClickListener{

        }

        //asigno el botón nevera
        val botonNevera = findViewById<ImageButton>(R.id.menuBotonNevera)
        botonNevera.setOnClickListener{

        }

        //asigno el botón perfil
        val botonPerfil = findViewById<ImageButton>(R.id.menuBotonPerfil)
        botonPerfil.setOnClickListener{
            //si el usuario está registrado, se muestra la activity que contiene datos de usuario

            //si el usuario no está registrado, se muestra la activity login
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }


}*/