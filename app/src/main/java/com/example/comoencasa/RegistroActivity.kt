package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.comoencasa.databinding.ActivityRegistroBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_registro)
        initUI()
    }

    private fun initUI(){
        Log.i("jeroana", "registro initui")
        val botonAcceder = findViewById<Button>(R.id.registroBoton)
        botonAcceder.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            val email = findViewById<TextInputEditText>(R.id.registroEmail).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.registroPass).text.toString()
            val user = findViewById<TextInputEditText>(R.id.registroUsuario).text.toString()
            if(validarDatos(email, pass, user)){
                val registerData = UserRequest(email, pass, user)
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        Log.i("jeroana", "registro corrutina")
                        val response: UserResponse? = Los70Fit.retrofitInstance.create(ApiService::class.java).newUser(registerData)
                        if (response != null){
                            Log.i("jeroana", response.toString())

                            intent.putExtra("userId", response.id)
                            intent.putExtra("userMail", response.email)
                            intent.putExtra("userPass", response.password)
                            intent.putExtra("userName", response.name)
                            intent.putParcelableArrayListExtra("userFavorites", ArrayList(response.recipesList))
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegistroActivity, "Los datos son incorrectos", Toast.LENGTH_LONG).show()
                        }
                    }
                    catch(e: SocketTimeoutException){
                        Toast.makeText(this@RegistroActivity, "solicitud ha excedido tiempo de espera", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun validarDatos(email: String, pass: String, user: String) : Boolean{
        if(email.isEmpty()){
            Toast.makeText(this@RegistroActivity, "Indica el email", Toast.LENGTH_LONG).show()
            return false
        }
        if(pass.isEmpty()){
            Toast.makeText(this@RegistroActivity, "Indica la contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        if(user.isEmpty()){
            Toast.makeText(this@RegistroActivity, "Indica el nombre de usuario", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}