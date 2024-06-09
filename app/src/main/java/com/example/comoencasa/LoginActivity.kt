package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.comoencasa.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        initUI()
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
            val intent = Intent (this, MainActivity::class.java)
            val email = findViewById<TextInputEditText>(R.id.loginUsuario).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.loginPass).text.toString()
            if (validarDatos(email, pass)) {
                val loginData = LoginRequest(email, pass)
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        Log.i("jeroana", "corrutina")
                        val response: UserResponse? =
                            Los70Fit.retrofitInstance.create(ApiService::class.java).getUser(loginData)
                        if (response != null) {
                            Log.i("jeroana", response.toString())

                            intent.putExtra("userId", response.id)
                            intent.putExtra("userMail", response.email)
                            intent.putExtra("userPass", response.password)
                            intent.putExtra("userName", response.name)
                            intent.putParcelableArrayListExtra("userFavorites", ArrayList(response.recipesList))
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Email o contraseña incorrectos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch(e: SocketTimeoutException){
                        Toast.makeText(this@LoginActivity, "solicitud ha excedido tiempo de espera", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}