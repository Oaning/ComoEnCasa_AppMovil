package com.example.comoencasa

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService{
    @POST("users/login")
    suspend fun getUser(@Body loginRequest: LoginRequest) : UserResponse?

    @GET("/users/{id}")
    suspend fun getUserDetail (@Path("id") id:String): Response<UserResponse>

    @GET("recipes/{id}")
    suspend fun getRecipeDetail (@Path("id") id:String): Response<RecipeResponse>

    @GET("ingredients/{id}")
    suspend fun getIngredientDetail (@Path("id") id:Int): Response<IngredientResponse>
}