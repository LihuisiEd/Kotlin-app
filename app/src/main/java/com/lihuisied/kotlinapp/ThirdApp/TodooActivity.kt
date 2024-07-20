package com.lihuisied.kotlinapp.ThirdApp

import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lihuisied.kotlinapp.R

class TodooActivity : AppCompatActivity() {

    private val categories = listOf(
        TaskCategory.Other,
        TaskCategory.Personal,
        TaskCategory.Business
    )

    private val tasks = mutableListOf(
        Task("Task of Business", TaskCategory.Business),
        Task("Task of Personal", TaskCategory.Personal),
        Task("Task of Other", TaskCategory.Other)
    )

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var fabAddTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todoo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
        initUI()
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAddTask: AppCompatButton = dialog.findViewById(R.id.btnAddTask)
        val edtTask: AppCompatEditText = dialog.findViewById(R.id.edtTask)
        val rdGroup: RadioGroup = dialog.findViewById(R.id.rdGroup)

        btnAddTask.setOnClickListener {
            val currentTask = edtTask.text.toString()
            if (currentTask.isNotEmpty()) {
                val selectedId = rdGroup.checkedRadioButtonId
                val selectedRadioButton: RadioButton = rdGroup.findViewById(selectedId)
                val currentCategory: TaskCategory = when (selectedRadioButton.text) {
                    getString(R.string.business) -> TaskCategory.Business
                    getString(R.string.personal) -> TaskCategory.Personal
                    else -> TaskCategory.Other
                }
                tasks.add(Task(currentTask, currentCategory))
                updateTasks()
                dialog.hide()
            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) { position -> updateCategories(position)}
        rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks) {position -> onItemSelected(position)}
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter
    }

    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun onItemSelected(position:Int){
        tasks[position].isSelected = ! tasks[position].isSelected
        updateTasks()
    }

    private fun updateCategories(position: Int){
        categories[position].isSelected = ! categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }

    private fun updateTasks() {
        val selectedCategories: List<TaskCategory> = categories.filter { it.isSelected }
        val newTasks = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = newTasks
        tasksAdapter.notifyDataSetChanged()
    }
}