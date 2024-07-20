package com.lihuisied.kotlinapp.Settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lihuisied.kotlinapp.R
import com.lihuisied.kotlinapp.databinding.ActivitySettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val BLUETOOTH_CHK = "bluetooth_chk"
        const val DARK_MODE = "dark_mode"
        const val VIBRATION = "vibration"
    }

    private lateinit var binding: ActivitySettingBinding
    private var firstTime:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect {
                if (it != null) {
                    runOnUiThread {
                        binding.switchDarkMode.isChecked = it.dark_mode
                        binding.switchBluetooth.isChecked = it.bluetooth
                        binding.switchVibracion.isChecked = it.vibration
                        binding.sliderVolumen.value = it.volume.toFloat()
                        firstTime = !firstTime
                    }
                }
            }
        }
        initUI()
    }

    private fun initUI() {
        binding.sliderVolumen.addOnChangeListener { slider, fl, b ->
            Log.i("Eddiian", "Valor: $fl")
            CoroutineScope(Dispatchers.IO).launch {
                saveIntVolume(fl.toInt())
            }
        }
        binding.switchBluetooth.setOnCheckedChangeListener { _, b ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(BLUETOOTH_CHK, b)
            }
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _, b ->
            if (b){
                enableDarkMode()
            } else {
                disableDarkMode()
            }
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(DARK_MODE, b)
            }
        }
        binding.switchVibracion.setOnCheckedChangeListener { _, b ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(VIBRATION, b)
            }
        }
    }

    private suspend fun saveIntVolume(value: Int) {
        dataStore.edit {
            it[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsMode?> {
        return dataStore.data.map {
            SettingsMode(
                volume = it[intPreferencesKey(VOLUME_LVL)] ?: 50,
                bluetooth = it[booleanPreferencesKey(BLUETOOTH_CHK)] ?: false,
                dark_mode = it[booleanPreferencesKey(DARK_MODE)] ?: false,
                vibration = it[booleanPreferencesKey(VIBRATION)] ?: true
            )

        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}