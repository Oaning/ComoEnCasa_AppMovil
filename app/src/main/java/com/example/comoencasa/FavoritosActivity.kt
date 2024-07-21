package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comoencasa.databinding.ActivityFavoritosBinding

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var adapter: FavoritosAdapter
    private var userId: Int = 0
    private var userFavorites: List<RecipeResponse> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", 0)
        userFavorites = intent.getParcelableArrayListExtra<RecipeResponse>("userFavorites") ?: emptyList()
        initUI()
    }

    private fun initUI(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean{
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchByName(newText.orEmpty())
                return true
            }
        })

        adapter = FavoritosAdapter{ recipe -> navigateToDetail(recipe)}
        binding.rvFavoritos.setHasFixedSize(true)
        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritos.adapter = adapter

        adapter.updateList(userFavorites)
    }

    private fun searchByName(query: String){
        val filteredList = if (query.isEmpty()) {
            userFavorites
        } else {
            userFavorites.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.updateList(filteredList)
    }

    private fun navigateToDetail(recipe: RecipeResponse){
        val intent = Intent(this, DetalleRecetaActivity::class.java)
        intent.putExtra("recipeId", recipe.id)
        intent.putExtra("userId", userId)
        intent.putExtra("recipeName", recipe.name)
        intent.putExtra("recipeDescription", recipe.description)
        intent.putExtra("recipePhoto", recipe.photo)
        val recipeIngredientsList = recipe.ingredientsList?.toTypedArray()
        intent.putExtra("recipeIngredients", recipeIngredientsList)
        intent.putParcelableArrayListExtra("userFavorites",
            userFavorites?.let { ArrayList(it) } ?: ArrayList())
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}