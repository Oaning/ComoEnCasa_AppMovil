package com.example.comoencasa

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comoencasa.MainActivity.Companion.REQUEST_CODE_PERFIL
import com.example.comoencasa.databinding.ActivityFavoritosBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var adapter: FavoritosAdapter
    private var userId: Int = 0
    private lateinit var userName: String
    private lateinit var userMail: String
    private lateinit var userPass: String
    private var userFavorites: MutableList<RecipeResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del Intent
        userId = intent.getIntExtra("userId", 0)
        userName = intent.getStringExtra("userName") ?: ""
        userMail = intent.getStringExtra("userMail") ?: ""
        userPass = intent.getStringExtra("userPass") ?: ""
        userFavorites = intent.getParcelableArrayListExtra<RecipeResponse>("userFavorites")?.toMutableList() ?: mutableListOf()

        iniciarMenu()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchByName(newText.orEmpty())
                return true
            }
        })

        adapter = FavoritosAdapter { recipe -> navigateToDetail(recipe) }
        binding.rvFavoritos.setHasFixedSize(true)
        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritos.adapter = adapter

        adapter.updateList(userFavorites)
    }

    private fun searchByName(query: String) {
        val filteredList = if (query.isEmpty()) {
            userFavorites
        } else {
            userFavorites.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.updateList(filteredList)
    }

    private fun navigateToDetail(recipe: RecipeResponse) {
        val intent = Intent(this, DetalleRecetaActivity::class.java).apply {
            putExtra("recipeId", recipe.id)
            putExtra("userId", userId)
            putExtra("recipeName", recipe.name)
            putExtra("recipeDescription", recipe.description)
            putExtra("recipePhoto", recipe.photo)
            putParcelableArrayListExtra("userFavorites", ArrayList(userFavorites))
            putExtra("userMail", userMail)
            putExtra("userPass", userPass)
            putExtra("userName", userName)
        }
        startActivity(intent)
    }

    private fun iniciarMenu() {
        setupMenuButton(R.id.menuBotonInicio, MainActivity::class.java)
        setupMenuButton(R.id.menuBotonPerfil, PerfilActivity::class.java, REQUEST_CODE_PERFIL)
        setupMenuButton(R.id.menuBotonMenuSemanal, MenuSemanalActivity::class.java, REQUEST_CODE_VOLVER)
        setupMenuButton(R.id.menuBotonNevera, NeveraActivity::class.java, REQUEST_CODE_VOLVER)
    }

    private fun setupMenuButton(buttonId: Int, targetActivity: Class<*>, requestCode: Int = -1) {
        findViewById<ImageButton>(buttonId).setOnClickListener {
            val intent = Intent(this, targetActivity).apply {
                putExtra("userId", userId)
                putExtra("userMail", userMail)
                putExtra("userPass", userPass)
                putExtra("userName", userName)
                putParcelableArrayListExtra("userFavorites", ArrayList(userFavorites))
            }
            if (requestCode == -1) {
                startActivity(intent)
            } else {
                startActivityForResult(intent, requestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                userId = it.getIntExtra("userId", 0)
                userName = it.getStringExtra("userName") ?: userName
                userMail = it.getStringExtra("userMail") ?: userMail
                userPass = it.getStringExtra("userPass") ?: userPass
                userFavorites = it.getParcelableArrayListExtra<RecipeResponse>("userFavorites")?.toMutableList() ?: mutableListOf()

                // Actualizar la UI con los nuevos datos del usuario
                adapter.updateList(userFavorites)
                iniciarMenu() // Para actualizar los listeners de los botones del menú con la nueva información
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PERFIL = 1001
        const val REQUEST_CODE_VOLVER = 1002
    }
}