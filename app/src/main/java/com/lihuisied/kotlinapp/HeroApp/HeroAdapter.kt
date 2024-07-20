package com.lihuisied.kotlinapp.HeroApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lihuisied.kotlinapp.R

class HeroAdapter(
    var herList: List<HeroItemResponse> = emptyList(),
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<HeroViewHolder>() {

    fun updateList(heroList: List<HeroItemResponse>) {
        herList = heroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false)
        )
    }

    override fun getItemCount() = herList.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(herList[position], onItemSelected)
    }
}