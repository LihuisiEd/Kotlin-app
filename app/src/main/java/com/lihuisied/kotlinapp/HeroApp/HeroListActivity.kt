package com.lihuisied.kotlinapp.HeroApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lihuisied.kotlinapp.HeroApp.HeroDetailActivity.Companion.EXTRA_ID
import com.lihuisied.kotlinapp.R
import com.lihuisied.kotlinapp.databinding.ActivityHeroListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HeroListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: HeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = HeroAdapter{navigateToDetail(it)}
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<HeroDataResponse> =
                retrofit.create(ApiService::class.java).getSuperHeroes(query)
            if (myResponse.isSuccessful) {
                val response: HeroDataResponse? = myResponse.body()
                if (response != null) {
                    Log.i("EddIIan", response.toString())
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                    }
                }
            } else {
                Log.i("EddIIan", "No funcionó... :(")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    private fun navigateToDetail(id:String){
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}