package com.example.comoencasa

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
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
        setContentView(R.layout.activity_detalle_receta)

        val recipeName = findViewById<TextView>(R.id.tvNombreReceta)
        val recipe = intent.getStringExtra("recipeName")
        recipeName.setText(recipe)

        val recipePhoto = findViewById<ImageView>(R.id.ivRecipePhoto)
        val photo = intent.getStringExtra("recipePhoto")
        Picasso.get()
            .load(photo)
            .into(recipePhoto)

        val ingredients: Array<String>?= intent.getStringArrayExtra("recipeIngredients")

        val adapter = ingredients?.let { RecetaAdapter(this, it) }
        val listView = findViewById<ListView>(R.id.lvRecetaIngredientes)
        listView.adapter = adapter

        val recipeDescription = findViewById<TextView>(R.id.tvDescripcionReceta)
        val description = intent.getStringExtra("recipeDescription")
        recipeDescription.setText(description)
    }
}

class RecetaAdapter (private val context: Context, val ingredientes: Array<String>) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ingredientes) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = ingredientes[position]
        return view
    }
}