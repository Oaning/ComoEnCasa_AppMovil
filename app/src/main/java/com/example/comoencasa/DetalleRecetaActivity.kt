package com.example.comoencasa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comoencasa.databinding.ActivityDetalleRecetaBinding
import com.example.comoencasa.databinding.ActivityFavoritosBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.net.SocketTimeoutException

class DetalleRecetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleRecetaBinding
    private var userFavorites: MutableList<RecipeResponse> = mutableListOf()
    private var recipeId: Int = 0
    private var userId: Int = 0
    private var auxUserId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        if(intent != null){
            userFavorites = intent.getParcelableArrayListExtra<RecipeResponse>("userFavorites")?.toMutableList() ?: mutableListOf()
            recipeId = intent.getIntExtra("recipeId", 0)
            val auxUserId = intent.getIntExtra("recipeId", 0)
        }
        userId = auxUserId



        val recipeName = binding.tvNombreReceta
        val recipe = intent.getStringExtra("recipeName")
        recipeName.text = recipe

        val recipePhoto = binding.ivRecipePhoto
        val photo = intent.getStringExtra("recipePhoto")
        val borderColor = Color.parseColor("#17615b")
        Picasso.get()
            .load(photo)
            .transform(ImageEdition(50, 10, borderColor, 10))
            .into(recipePhoto)

        val ingredients: Array<String>?= intent.getStringArrayExtra("recipeIngredients")
        val ingredientList = ingredients?.map { Ingrediente(it) } ?: emptyList()

        val adapter = IngredienteAdapter(ingredientList)
        val recyclerView = binding.rvRecetaIngredientes
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val recipeDescription = binding.tvDescripcionReceta
        val description = intent.getStringExtra("recipeDescription")
        recipeDescription.text = description

        setupFavoriteButton(userId)
    }

    private fun setupFavoriteButton(userId: Int) {
        val button = binding.botonReceta
        val isFavorite = updateButtonState(button)

        button.setOnClickListener {
            if (isFavorite) {
                removeFromFavorites(userId)
            } else {
                addToFavorites(userId)
            }
            updateButtonState(button)
            val intent = Intent(this, FavoritosActivity::class.java)
            intent.putParcelableArrayListExtra("userFavorites",
                userFavorites?.let { ArrayList(it) } ?: ArrayList())
            startActivity(intent)
        }
    }

    private fun updateButtonState(button: Button):Boolean {
        val isFavorite = userFavorites.any { it.id == recipeId }
        if (isFavorite) {
            button.text = getString(R.string.receta_quitar)
        } else {
            button.text = getString(R.string.receta_guardar)
        }
        return isFavorite
    }

    private fun addToFavorites(userId: Int) {
        val recipe = intent.getStringExtra("recipePhoto")?.let {
            RecipeResponse(
                recipeId,
                binding.tvNombreReceta.text.toString(),
                it,
                binding.tvDescripcionReceta.text.toString(),
                intent.getStringArrayExtra("recipeIngredients")?.toList() ?: emptyList()
            )
        }
        if (recipe != null) {
            userFavorites.add(recipe)
        }
        updateFavoritesInServer(UserRecipeDTO(userId, recipeId), true)
    }

    private fun removeFromFavorites(userId: Int) {
        userFavorites.removeAll { it.id == recipeId }
        updateFavoritesInServer(UserRecipeDTO(userId, recipeId), false)
    }

    private fun updateFavoritesInServer(userRecipeDTO: UserRecipeDTO, add: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = Los70Fit.retrofitInstance.create(ApiService::class.java)
                if (add) {
                    service.addRecipeToUser(userRecipeDTO)
                    Log.i("jeroana", "se añade receta")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetalleRecetaActivity, "Receta añadida", Toast.LENGTH_LONG).show()
                    }
                } else {
                    service.deleteRecipeFromUser(userRecipeDTO)
                    Log.i("jeroana", "se quita receta")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetalleRecetaActivity, "Receta eliminada", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: SocketTimeoutException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetalleRecetaActivity, "Solicitud ha excedido tiempo de espera", Toast.LENGTH_LONG).show()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("jeroana", "HTTP error: ${e.code()}, $errorBody")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetalleRecetaActivity, "Error HTTP: ${e.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.i("jeroana", "no se puede añadir ni quitar receta")
                Log.i("jeroana", e.stackTraceToString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetalleRecetaActivity, "Error guardando o quitando receta", Toast.LENGTH_LONG).show()
                }
                e.printStackTrace()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
    }
}

data class Ingrediente(val nombre: String)

class IngredienteAdapter(private val ingredientes: List<Ingrediente>) : RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreIngrediente: TextView = itemView.findViewById(R.id.tvIngrediente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder.nombreIngrediente.text = ingrediente.nombre
    }

    override fun getItemCount() = ingredientes.size
}