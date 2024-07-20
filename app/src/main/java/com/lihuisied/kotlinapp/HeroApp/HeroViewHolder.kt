package com.lihuisied.kotlinapp.HeroApp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lihuisied.kotlinapp.databinding.ItemHeroBinding
import com.squareup.picasso.Picasso

class HeroViewHolder(view:View): RecyclerView.ViewHolder(view) {

    private val binding = ItemHeroBinding.bind(view)

    fun bind(heroItemResponse: HeroItemResponse, onItemSelected: (String) -> Unit){
        binding.txtHeroName.text = heroItemResponse.hero_name
        Picasso.get().load(heroItemResponse.hero_image.url).into(binding.imgView)
        binding.root.setOnClickListener { onItemSelected(heroItemResponse.hero_id) }
    }
}