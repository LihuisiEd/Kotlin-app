package com.lihuisied.kotlinapp.ThirdApp

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lihuisied.kotlinapp.R

class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val txtTaskName: TextView = view.findViewById(R.id.txtTaskName)
    private val checkboxTask: AppCompatCheckBox = view.findViewById(R.id.checkboxTask)

    fun render(task: Task) {
        if (task.isSelected) {
            txtTaskName.paintFlags = txtTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            txtTaskName.paintFlags = txtTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        txtTaskName.text = task.name
        checkboxTask.isChecked = task.isSelected

        val color = when (task.category) {
            TaskCategory.Business -> R.color.indicator_blue
            TaskCategory.Other -> R.color.yellow_500
            TaskCategory.Personal -> R.color.indicator_green
        }

        checkboxTask.buttonTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                checkboxTask.context,
                color
            )
        )

    }
}