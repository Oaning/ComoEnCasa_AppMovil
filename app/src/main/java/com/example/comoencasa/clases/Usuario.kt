package com.example.comoencasa.clases

import com.google.gson.annotations.SerializedName

data class UsuarioRespuesta (
    @SerializedName("idusuario") val idusuario: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("listaFavoritos") val listaFavoritos: List<ListaFavoritosRespuesta>
)

data class ListaFavoritosRespuesta(
    @SerializedName("idreceta") val idreceta: Int,
    @SerializedName("nombre_receta") val nombre_receta: String,
    @SerializedName("ingrediente_receta") val ingrediente_receta:  List<ListaIngredientesRespuesta>
)

data class ListaIngredientesRespuesta(
    @SerializedName("id_ingrediente") val id_ingrediente: Int,
    @SerializedName("nombre_ingrediente") val nombre_ingrediente: String,
    @SerializedName("tipo") val tipo: Int
)