package com.example.comoencasa

import android.app.Activity
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
        val userId = intent.getIntExtra("userId", 0)
        val userName = intent.getStringExtra("userName")
        val userMail = intent.getStringExtra("userMail")
        val userPass = intent.getStringExtra("userPass")
        val userFavs: List<RecipeResponse>? = intent.getParcelableArrayListExtra("userFavorites")
        rellenarDatos(userId, userName, userMail, userPass, userFavs)
    }

    private fun rellenarDatos(userId: Int, userName: String?, userMail: String?, userPass: String?, userFavs: List<RecipeResponse>?) {
        val name = findViewById<EditText>(R.id.perfilRellenarUsuario)
        name.setText(userName)

        val mail = findViewById<EditText>(R.id.perfilRellenarEmail)
        mail.setText(userMail)

        val pass = findViewById<EditText>(R.id.perfilRellenarContraseña)
        pass.setText(userPass)

        val btnCancelar = findViewById<Button>(R.id.perfilBotonCancelar)
        btnCancelar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("userId", userId)
            intent.putExtra("userMail", userMail)
            intent.putExtra("userPass", userPass)
            intent.putExtra("userName", userName)
            intent.putParcelableArrayListExtra("userFavorites", ArrayList(userFavs))
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        val btnAceptar = findViewById<Button>(R.id.perfilBotonAceptar)
        btnAceptar.setOnClickListener {
            val chngEmail = mail.text.toString()
            val chngName = name.text.toString()
            val chngPass = pass.text.toString()
            if(comprobarDatos(chngEmail, chngName, chngPass)) {
                val userData = UserDataRequest(userId, chngEmail, chngName, chngPass, userFavs)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.i("jeroana", "corrutina modificar perfil")
                        val response: UserResponse? =
                            Los70Fit.retrofitInstance.create(ApiService::class.java).updateUser(userData)
                        if (response != null) {
                            Log.i("jeroana", response.toString())

                            runOnUiThread {
                                Toast.makeText(this@PerfilActivity, "Datos modificados correctamente", Toast.LENGTH_LONG).show()

                                val resultIntent = Intent()
                                resultIntent.putExtra("userId", response.id)
                                resultIntent.putExtra("userMail", response.email)
                                resultIntent.putExtra("userPass", response.password)
                                resultIntent.putExtra("userName", response.name)
                                resultIntent.putParcelableArrayListExtra("userFavorites", ArrayList(response.recipesList))

                                rellenarDatos(response.id, response.name, response.email, response.password, response.recipesList)
                                setResult(Activity.RESULT_OK, resultIntent)
                                finish()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@PerfilActivity, "Los datos no han podido modificarse", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: SocketTimeoutException) {
                        runOnUiThread {
                            Toast.makeText(this@PerfilActivity, "Solicitud ha excedido tiempo de espera", Toast.LENGTH_LONG).show()
                        }
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