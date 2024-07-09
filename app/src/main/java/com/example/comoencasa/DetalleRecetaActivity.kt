package com.example.comoencasa

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comoencasa.databinding.ActivityDetalleRecetaBinding
import com.example.comoencasa.databinding.ActivityFavoritosBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetalleRecetaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetalleRecetaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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