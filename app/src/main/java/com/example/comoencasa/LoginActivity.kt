package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.comoencasa.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityLoginBinding
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        retrofit = getRetrofit()
        initUI()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.1.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initUI(){
        //si el usuario no está registrado y se quiere registrar, se le manda a la página de registro
        val botonRegistrarse = findViewById<Button>(R.id.loginBotonRegistrarse)
        botonRegistrarse.setOnClickListener {
            val intentRegistro = Intent(this, RegistroActivity::class.java)
            startActivity(intentRegistro)
        }

        //en caso de que esté registrado, se recogen los datos email y contraseña para intentar iniciar sesión
        iniciarSesion();
        Log.i("jeroana", "initui")
    }

    private fun validarDatos(email: String, pass: String) : Boolean{
        if(email.isEmpty()){
            Toast.makeText(this@LoginActivity, "Indica el email", Toast.LENGTH_LONG).show()
            return false
        }
        if(pass.isEmpty()){
            Toast.makeText(this@LoginActivity, "Indica la contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun iniciarSesion(){
        val botonAcceder = findViewById<Button>(R.id.loginBotonAcceder)
        botonAcceder.setOnClickListener {
            val intentAcceso = Intent (this, MainActivity::class.java)
            val email = findViewById<TextInputEditText>(R.id.loginUsuario).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.loginPass).text.toString()
            if (validarDatos(email, pass)) {
                val loginData = LoginRequest(email, pass)
                CoroutineScope(Dispatchers.IO).launch {
                    val response: UserResponse? = retrofit.create(ApiService::class.java).getUser(loginData)
                    if (response != null) {
                        Log.i("jeroana", response.toString())
                        startActivity(intentAcceso)
                    } else {
                        Toast.makeText(this@LoginActivity, "Email o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}