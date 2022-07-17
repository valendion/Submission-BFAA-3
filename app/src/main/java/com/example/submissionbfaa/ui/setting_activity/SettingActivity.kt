package com.example.submissionbfaa.ui.setting_activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.submissionbfaa.R
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.databinding.ActivityMainBinding
import com.example.submissionbfaa.databinding.ActivitySettingBinding
import com.example.submissionbfaa.ui.favorite_activity.FavoriteActivity
import org.koin.android.ext.android.inject

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val factory: SettingViewModelFactory by inject()
    private val settingViewModel: SettingViewModel by viewModels {factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.settingToolbar)

        binding.apply {
            settingViewModel.getThemeSetting().observe(this@SettingActivity){isDarkMode: Boolean ->
                if (isDarkMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    darkModeSwitch.isChecked = true
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    darkModeSwitch.isChecked = false
                }
            }

            darkModeSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}