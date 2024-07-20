package com.lihuisied.kotlinapp.ThirdApp

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.lihuisied.kotlinapp.R

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val txtCategory: TextView = view.findViewById(R.id.txtCategory)
    private val cardCategory: LinearLayoutCompat = view.findViewById(R.id.cardCategory)

    fun render(taskCategory: TaskCategory, onItemSelected: (Int) -> Unit) {

        val background = if (taskCategory.isSelected){
            R.drawable.custom_gradient_background
        } else{
            R.drawable.custom_card
        }

        cardCategory.setBackgroundResource(background)
        itemView.setOnClickListener {  onItemSelected(layoutPosition) }

        when (taskCategory) {
            TaskCategory.Business -> {
                txtCategory.text = "Negocios"
            }

            TaskCategory.Other -> {
                txtCategory.text = "Otros"
            }

            TaskCategory.Personal -> {
                txtCategory.text = "Personal"
            }
        }
    }
}