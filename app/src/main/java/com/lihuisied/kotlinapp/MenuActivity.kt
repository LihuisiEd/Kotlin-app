package com.lihuisied.kotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lihuisied.kotlinapp.FirstApp.MainActivity
import com.lihuisied.kotlinapp.HeroApp.HeroListActivity
import com.lihuisied.kotlinapp.SecondApp.IMCActivity
import com.lihuisied.kotlinapp.Settings.SettingActivity
import com.lihuisied.kotlinapp.ThirdApp.TodooActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnSaludo = findViewById<AppCompatButton>(R.id.btnSaludo)
        val btnIMC = findViewById<AppCompatButton>(R.id.btnIMC)
        val btnTodoo = findViewById<AppCompatButton>(R.id.btnTodoo)
        val btnHerList = findViewById<AppCompatButton>(R.id.btnHeroList)
        val btnSettings = findViewById<AppCompatButton>(R.id.btnSettings)
        btnSaludo.setOnClickListener { navegateToSaludo() }
        btnIMC.setOnClickListener { navegateToIMC() }
        btnTodoo.setOnClickListener { navegateToTodoo() }
        btnHerList.setOnClickListener { navigateToHerList() }
        btnSettings.setOnClickListener { navigateToSettings() }
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun navegateToTodoo() {
        val intent = Intent(this, TodooActivity::class.java)
        startActivity(intent)
    }

    private fun navegateToSaludo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navegateToIMC() {
        val intent = Intent(this, IMCActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHerList(){
        val intent = Intent(this, HeroListActivity::class.java)
        startActivity(intent)
    }
}