package com.lihuisied.kotlinapp.SecondApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.lihuisied.kotlinapp.R
import java.text.DecimalFormat

class IMCActivity : AppCompatActivity() {

    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = false
    private var currentWeight:Int = 60
    private var currentAge:Int = 18
    private var currentHeight:Int = 120

    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView
    private lateinit var txtHeight:TextView
    private lateinit var sldHeight:RangeSlider
    private lateinit var btnEnviar:Button
    //Weight
    private lateinit var txtWeight:TextView
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    //Age
    private lateinit var txtAge:TextView
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton

    companion object {
        const val IMC_KEY = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imcactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponent()
        initListeners()
        initUI()
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }

    private fun changeGender(){
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        sldHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            txtHeight.text = "$currentHeight cm"
        }
        btnEnviar.setOnClickListener {
            val result = calcularIMC()
            navigationResult(result)
        }
        //Weight
        btnSubstractWeight.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }
        btnPlusWeight.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        //Age
        btnSubstractAge.setOnClickListener {
            currentAge -= 1
            setAge()
        }
        btnPlusAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
    }

    private fun navigationResult(result: Double) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calcularIMC():Double {
        val df = DecimalFormat("#.##")
        val imc = currentWeight/(currentHeight.toDouble()/100*currentHeight.toDouble()/100)
        return df.format(imc).toDouble()
    }

    private fun setAge() {
        txtAge.text = currentAge.toString()
    }

    private fun setWeight() {
        txtWeight.text = currentWeight.toString()
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent:Boolean):Int{
        val colorReference = if(isSelectedComponent){
            R.color.amber_500
        }else{
            R.color.amber_300
        }
        return ContextCompat.getColor(this,colorReference)
    }

    private fun initComponent(){
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        txtHeight = findViewById(R.id.txtHeight)
        sldHeight = findViewById(R.id.sldHeight)
        btnEnviar = findViewById(R.id.btnEnviar)
        //Weight
        btnSubstractWeight = findViewById(R.id.btnSubstractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        txtWeight = findViewById(R.id.txtWeight)
        //Age
        txtAge = findViewById(R.id.txtAge)
        btnSubstractAge = findViewById(R.id.btnSubstractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)

    }
}