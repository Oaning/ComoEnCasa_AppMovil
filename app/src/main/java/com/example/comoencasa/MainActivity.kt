package com.example.comoencasa

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.comoencasa.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val intent = intent
        val userId = intent.getIntExtra("userId", 0)
        val userName = intent.getStringExtra("userName")
        val userMail = intent.getStringExtra("userMail")
        val userPass = intent.getStringExtra("userPass")
        val userFavorites: List<RecipeResponse> = intent.getParcelableArrayListExtra("userFavorites")?: emptyList()
        cargarRecetaRandom(userId, userFavorites)
        iniciarMenu(userId, userName, userMail, userPass, userFavorites)
    }

    private fun cargarRecetaRandom(userId: Int, userFavorites: List<RecipeResponse>){
        val itemRecipeView = findViewById<LinearLayout>(R.id.item_recipe_container)
        val recipeImage = itemRecipeView.findViewById<ImageView>(R.id.ivRecipe)
        val recipeName = itemRecipeView.findViewById<TextView>(R.id.ivRecetaNombre)
        var respIdReceta: Int? = null
        var respNomReceta: String? = null
        var respImgReceta: String? = null
        var respDescReceta: String? = null
        var respIngReceta: List<String>? = null
        CoroutineScope(Dispatchers.IO).launch {
            try{
                Log.i("jeroana", "corrutina main")
                val response: RecipeResponse = Los70Fit.retrofitInstance.create(ApiService::class.java).getRandomRecipe()
                runOnUiThread {
                    Log.i("jeroana", response.toString())
                    respIdReceta = response.id
                    respNomReceta = response.name
                    respImgReceta = response.photo
                    respDescReceta = response.description
                    respIngReceta = response.ingredientsList
                    Picasso.get().load(respImgReceta).into(recipeImage)
                    recipeName.text = respNomReceta
                }

            } catch(e: SocketTimeoutException){
                Toast.makeText(this@MainActivity, "solicitud ha excedido tiempo de espera", Toast.LENGTH_LONG).show()
            }
        }

        itemRecipeView.setOnClickListener {
            val intent = Intent(this, DetalleRecetaActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("recipeId", respIdReceta)
            intent.putExtra("recipeName", respNomReceta)
            intent.putExtra("recipeDescription", respDescReceta)
            intent.putExtra("recipePhoto", respImgReceta)
            val recipeIngredientsList = respIngReceta?.toTypedArray()
            intent.putExtra("recipeIngredients", recipeIngredientsList)
            intent.putParcelableArrayListExtra("userFavorites",
                userFavorites?.let { ArrayList(it) } ?: ArrayList())
            startActivity(intent)
        }
    }

    private fun iniciarMenu(userId: Int, userName: String?, userMail: String?, userPass: String?, userFavorites: List<RecipeResponse>?){
        verFavoritos(userFavorites, userId)
        verPerfil(userId, userName, userMail, userPass, userFavorites)
        verMenuSemanal()
        verNevera()
    }

    private fun verFavoritos(userFavorites: List<RecipeResponse>?, userId: Int){
        val botonFavoritos = findViewById<ImageButton>(R.id.menuBotonFavoritos)
        botonFavoritos.setOnClickListener{
            val intent = Intent(this, FavoritosActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putParcelableArrayListExtra("userFavorites",
                userFavorites?.let { ArrayList(it) } ?: ArrayList())
            startActivity(intent)
        }
    }

    private fun verPerfil(userId: Int, userName: String?, userMail: String?, userPass: String?, userFavorites: List<RecipeResponse>?){
        val botonPerfil = findViewById<ImageButton>(R.id.menuBotonPerfil)
        botonPerfil.setOnClickListener{
            val intent = Intent(this, PerfilActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("userMail", userMail)
            intent.putExtra("userPass", userPass)
            intent.putExtra("userName", userName)
            intent.putParcelableArrayListExtra("userFavorites",
                userFavorites?.let { it1 -> ArrayList(it1) })
            startActivityForResult(intent, REQUEST_CODE_PERFIL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PERFIL && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val userId = data.getIntExtra("userId", 0)
                val userName = data.getStringExtra("userName")
                val userMail = data.getStringExtra("userMail")
                val userPass = data.getStringExtra("userPass")
                val userFavorites: List<RecipeResponse>? = data.getParcelableArrayListExtra("userFavorites")
                // Actualiza los datos del usuario en la MainActivity
                iniciarMenu(userId, userName, userMail, userPass, userFavorites)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PERFIL = 1001
    }
    private fun verMenuSemanal(){
        val botonMenuSemanal = findViewById<ImageButton>(R.id.menuBotonMenuSemanal)
        botonMenuSemanal.setOnClickListener {
            val intentMenuSemanal = Intent(this, MenuSemanalActivity::class.java)
            startActivity(intentMenuSemanal)
        }
    }

    private fun verNevera(){
        val botonNevera = findViewById<ImageButton>(R.id.menuBotonNevera)
        botonNevera.setOnClickListener {
            val intentNevera = Intent(this, NeveraActivity::class.java)
            startActivity(intentNevera)
        }
    }
}