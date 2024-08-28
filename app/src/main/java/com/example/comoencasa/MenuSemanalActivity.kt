package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuSemanalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_semanal)

        val intent = intent
        val userId = intent.getIntExtra("userId", 0)
        val userName = intent.getStringExtra("userName")
        val userMail = intent.getStringExtra("userMail")
        val userPass = intent.getStringExtra("userPass")
        val userFavorites: List<RecipeResponse> = intent.getParcelableArrayListExtra("userFavorites")?: emptyList()

        val botonVolver = findViewById<Button>(R.id.menusemanalBotonVolver)
        botonVolver.setOnClickListener{
            val intentVolver = Intent(this, MainActivity::class.java)

            intentVolver.putExtra("userId", userId)
            intentVolver.putExtra("userMail", userMail)
            intentVolver.putExtra("userPass", userPass)
            intentVolver.putExtra("userName", userName)
            intentVolver.putParcelableArrayListExtra("userFavorites", ArrayList(userFavorites))

            setResult(RESULT_OK, intentVolver)
            finish()
        }
    }
}