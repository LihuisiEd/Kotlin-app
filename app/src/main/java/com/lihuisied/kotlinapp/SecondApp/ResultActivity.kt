package com.lihuisied.kotlinapp.SecondApp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lihuisied.kotlinapp.R
import com.lihuisied.kotlinapp.SecondApp.IMCActivity.Companion.IMC_KEY

class ResultActivity : AppCompatActivity() {

    private lateinit var txtResultado: TextView
    private lateinit var txtImc: TextView
    private lateinit var txtDescription: TextView

    private lateinit var  btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initUI(result)

    }

    private fun initListeners() {
        btnEnviar.setOnClickListener { onBackPressed() }
    }

    private fun initUI(result: Double) {
        txtResultado.text = result.toString()
        when (result) {
            in 0.00..18.50 -> { // Bajo peso
                txtImc.text = getString(R.string.title_low_weight)
                txtImc.setTextColor(ContextCompat.getColor(this, R.color.low_weight))
                txtDescription.text = getString(R.string.desc_low_weight)
            }

            in 18.51..24.99 -> { // Peso normal
                txtImc.text = getString(R.string.title_normal_weight)
                txtImc.setTextColor(ContextCompat.getColor(this, R.color.normal_weight))
                txtDescription.text = getString(R.string.desc_normal_weight)
            }

            in 25.00..29.99 -> { // Sobrepeso
                txtImc.text = getString(R.string.title_over_weight)
                txtImc.setTextColor(ContextCompat.getColor(this, R.color.over_weight))
                txtDescription.text = getString(R.string.desc_over_weight)
            }

            in 30.00..99.99 -> { // Obesidad
                txtImc.text = getString(R.string.title_high_weight)
                txtImc.setTextColor(ContextCompat.getColor(this, R.color.high_weight))
                txtDescription.text = getString(R.string.desc_high_weight)
            }

            else -> { // Error
                txtImc.text = getString(R.string.error)
                txtResultado.text = getString(R.string.error)
                txtDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initComponents() {
        txtResultado = findViewById(R.id.txtResultado)
        txtImc = findViewById(R.id.txtImc)
        txtDescription = findViewById(R.id.txtDescription)

        btnEnviar = findViewById(R.id.btnEnviar)
    }
}