package com.example.comoencasa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoritosAdapter (
    var recipeList: List<RecipeResponse> = emptyList(),
    private val onItemSelected: (RecipeResponse) -> Unit
) :
    RecyclerView.Adapter<FavoritosViewHolder>(){

    fun updateList(list: List<RecipeResponse>) {
        recipeList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder{
        return FavoritosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: FavoritosViewHolder, position: Int){
        viewHolder.bind(recipeList[position], onItemSelected)
    }

    override fun getItemCount() = recipeList.size
}
