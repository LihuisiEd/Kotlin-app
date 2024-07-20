package com.lihuisied.kotlinapp.FirstApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lihuisied.kotlinapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnEnviar = findViewById<AppCompatButton>(R.id.btnEnviar)
        val edtText = findViewById<AppCompatEditText>(R.id.edtText)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnEnviar.setOnClickListener {
            //Log.i("Eddiian", "Click realizado!")
            val name = edtText.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, IntentActivity::class.java)
                intent.putExtra("NAME", name)
                startActivity(intent)
            }
        }
    }
}