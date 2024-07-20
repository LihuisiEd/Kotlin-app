package com.lihuisied.kotlinapp.ThirdApp

data class Task(
    val name: String,
    val category: TaskCategory,
    var isSelected: Boolean = false
)
