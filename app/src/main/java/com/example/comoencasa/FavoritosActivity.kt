package com.example.comoencasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comoencasa.DetalleRecetaActivity.Companion.EXTRA_ID
import com.example.comoencasa.databinding.ActivityFavoritosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: FavoritosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_favoritos)
        retrofit = getRetrofit()
        initUI()
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.1.106")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initUI(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean{
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        adapter = FavoritosAdapter{ id -> navigateToDetail(id)}
        binding.rvFavoritos.setHasFixedSize(true)
        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritos.adapter = adapter
    }

    private fun searchByName(query: String){
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<UserResponse> = retrofit.create(ApiService::class.java).getUserDetail(query)
            if(myResponse.isSuccessful){
                Log.i("jeroana", "funciona")
                val response: UserResponse? = myResponse.body()
                if(response != null){
                    Log.i("jeroana", response.toString())
                    runOnUiThread {
                        adapter.updateList(response.recipesList)
                        binding.progressBar.isVisible = false
                    }
                }
            }
            else {
                Log.i("jeroana", "No funciona")
            }
        }
    }

    private fun navigateToDetail(id: Int){
        val intent = Intent(this, DetalleRecetaActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}