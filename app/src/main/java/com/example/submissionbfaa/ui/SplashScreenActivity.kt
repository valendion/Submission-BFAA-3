package com.example.submissionbfaa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.submissionbfaa.R
import com.example.submissionbfaa.ui.main_activity.MainActivity
import com.example.submissionbfaa.ui.setting_activity.SettingViewModel
import com.example.submissionbfaa.ui.setting_activity.SettingViewModelFactory
import com.example.submissionbfaa.utils.CoroutineHelper
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class SplashScreenActivity : AppCompatActivity() {

    private val factory: SettingViewModelFactory by inject()
    private val settingViewModel: SettingViewModel by viewModels {factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        settingViewModel.getThemeSetting().observe(this){isDarkMode: Boolean ->
            if (isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        CoroutineHelper.main {
            delay(3000)
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

    }
}