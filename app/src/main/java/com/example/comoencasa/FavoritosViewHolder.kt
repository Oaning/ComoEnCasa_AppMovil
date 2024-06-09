package com.example.comoencasa

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.comoencasa.databinding.ItemRecipeBinding
import com.squareup.picasso.Picasso

class FavoritosViewHolder (view: View) : RecyclerView.ViewHolder(view){
    private val binding = ItemRecipeBinding.bind(view)

    fun bind(recipeItemResponse: RecipeResponse, onItemSelected: (Int) -> Unit){
        binding.ivRecetaNombre.text = recipeItemResponse.name
        Picasso.get().load(recipeItemResponse.photo).into(binding.ivRecipe)
        binding.root.setOnClickListener{onItemSelected(recipeItemResponse.id)}
    }
}