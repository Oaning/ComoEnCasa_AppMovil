package com.example.comoencasa

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
data class UserResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_recipe") val userRecipe: List<RecipeResponse>
)

data class RecipeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: RecipePhotoResponse,
    @SerializedName("recipe_ingredient") val recipeIngredient:  List<IngredientResponse>
)

data class RecipePhotoResponse(
    @SerializedName("url") val url:String
)

data class IngredientResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("from_month") val fromMonth: Int,
    @SerializedName("to_month") val toMonth: Int
)

