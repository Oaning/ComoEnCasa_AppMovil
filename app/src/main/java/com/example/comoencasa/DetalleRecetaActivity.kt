package com.example.comoencasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getRecipeInformation(id)
    }

    private fun getRecipeInformation(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            val recipeDetail = getRetrofit().create(ApiService::class.java).getRecipeDetail(id)

            if(recipeDetail.body() != null){
                runOnUiThread { createUI(recipeDetail.body()!!) }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.1.106")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createUI(recipe: RecipeResponse){
        Picasso.get().load(recipe.photo).into(binding.ivRecipe)
    }
}