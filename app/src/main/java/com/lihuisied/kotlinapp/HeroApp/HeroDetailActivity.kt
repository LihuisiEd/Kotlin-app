package com.lihuisied.kotlinapp.HeroApp

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lihuisied.kotlinapp.R
import com.lihuisied.kotlinapp.databinding.ActivityHeroDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class HeroDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityHeroDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHeroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id:String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getHeroInformation(id)
    }

    private fun getHeroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val heroDetail = getRetrofit().create(ApiService::class.java).getHeroDetail(id)
            if (heroDetail.body() != null){
                runOnUiThread { createUI(heroDetail.body()!!) }
            }
        }
    }

    private fun createUI(hero: HeroDetailResponse) {
        Picasso.get().load(hero.image.url).into(binding.imgHeroDetail)
        binding.txtFullName.text = hero.biography.fullName
        binding.txtNameDetail.text = hero.name
        binding.txtPublisher.text = hero.biography.publisher
        binding.txtGender.text = hero.appearance.gender
        binding.txtRace.text = hero.appearance.race
        binding.txtHeight.text = hero.appearance.height[1]
        prepareStats(hero.powerstats)
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        updateHeight(binding.viewCombat, powerstats.combat)
        updateHeight(binding.viewDurability, powerstats.durability)
        updateHeight(binding.viewSpeed, powerstats.speed)
        updateHeight(binding.viewStrength, powerstats.strength)
        updateHeight(binding.viewIntelligence, powerstats.intelligence)
        updateHeight(binding.viewPower, powerstats.power)
    }

    private fun updateHeight(view: View, stat:String){
        val params = view.layoutParams
        params.height = pxToDp(stat.toFloat())
        view.layoutParams = params
    }

    private fun pxToDp(px:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}