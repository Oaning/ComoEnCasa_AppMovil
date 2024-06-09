package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        val userName = intent.getStringExtra("userName")
        val userMail = intent.getStringExtra("userMail")
        val userPass = intent.getStringExtra("userPass")

        rellenarDatos(userName, userMail, userPass)
    }

    private fun rellenarDatos(userName: String?, userMail: String?, userPass: String?) {
        val name = findViewById<EditText>(R.id.perfilRellenarUsuario)
        name.setText(userName)

        val mail = findViewById<EditText>(R.id.perfilRellenarEmail)
        mail.setText(userMail)

        val pass = findViewById<EditText>(R.id.perfilRellenarContraseña)
        pass.setText(userPass)

        val btnAceptar = findViewById<Button>(R.id.perfilBotonAceptar)
        btnAceptar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnModificar = findViewById<Button>(R.id.perfilBotonModificar)
        btnModificar.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            val email = findViewById<EditText>(R.id.perfilRellenarEmail).text.toString()
            val name = findViewById<EditText>(R.id.perfilRellenarUsuario).text.toString()
            val pass = findViewById<EditText>(R.id.perfilRellenarContraseña).text.toString()
            val userData = UserRequest(email, name, pass)
            if(comprobarDatos(email, name, pass)) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.i("jeroana", "corrutina modificar perfil")
                        val response: UserResponse? =
                            Los70Fit.retrofitInstance.create(ApiService::class.java).updateUser(userData)
                        if (response != null) {
                            Log.i("jeroana", response.toString())

                            Toast.makeText(this@PerfilActivity, "Datos modificados correctamente", Toast.LENGTH_LONG).show()

                            intent.putExtra("userId", response.id)
                            intent.putExtra("userMail", response.email)
                            intent.putExtra("userPass", response.password)
                            intent.putExtra("userName", response.name)
                            intent.putParcelableArrayListExtra("userFavorites",ArrayList(response.recipesList))

                            startActivity(intent)
                        } else {
                            Toast.makeText(this@PerfilActivity,"Los datos no han podido modificarse",Toast.LENGTH_LONG).show()
                        }
                    } catch (e: SocketTimeoutException) {
                        Toast.makeText(this@PerfilActivity,"Solicitud ha excedido tiempo de espera",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun comprobarDatos(email: String, name: String, pass: String) : Boolean{
        val nameOrig = intent.getStringExtra("userName")
        val mailOrig = intent.getStringExtra("userMail")
        val passOrig = intent.getStringExtra("userPass")
        if(email.isEmpty()){
            Toast.makeText(this@PerfilActivity, "Indica el email", Toast.LENGTH_LONG).show()
            return false
        }
        if(name.isEmpty()){
            Toast.makeText(this@PerfilActivity, "Indica el nombre", Toast.LENGTH_LONG).show()
            return false
        }
        if(pass.isEmpty()){
            Toast.makeText(this@PerfilActivity, "Indica la contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        if(nameOrig.equals(name) && mailOrig.equals(email) && passOrig.equals(pass)){
            Toast.makeText(this@PerfilActivity, "Para modificar los datos, debe introducir valores distintos a los que ya había", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}