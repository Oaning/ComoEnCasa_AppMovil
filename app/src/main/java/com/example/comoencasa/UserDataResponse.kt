package com.example.comoencasa

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class UserRecipeDTO(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recipe_id")val recipeId: Int
)

data class UserRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String
)
data class UserDataRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("recipesList") val recipesList: List<RecipeResponse>?
)
data class UserResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("recipesList") val recipesList: List<RecipeResponse>
)

@Parcelize
data class RecipeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("description") val description: String,
    @SerializedName("ingredientsList") val ingredientsList: List<String>? = emptyList()
) : Parcelable
@Parcelize
data class IngredientResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("from_month") val fromMonth: Int,
    @SerializedName("to_month") val toMonth: Int
) : Parcelable