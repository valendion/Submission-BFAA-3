package com.example.submissionbfaa.ui.setting_activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.submissionbfaa.databinding.ActivitySettingBinding
import com.example.submissionbfaa.ui.main_activity.MainActivity
import com.example.submissionbfaa.utils.LocaleHelper
import org.koin.android.ext.android.inject

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val factory: SettingViewModelFactory by inject()
    private val settingViewModel: SettingViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.settingToolbar)

        binding.apply {
            settingViewModel.getThemeSetting()
                .observe(this@SettingActivity) { isDarkMode: Boolean ->
                    if (isDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        darkModeSwitch.isChecked = true
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        darkModeSwitch.isChecked = false
                    }
                }

            darkModeSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }

            settingViewModel.getLocaleSetting().observe(this@SettingActivity) { isLocaleEnglish ->
                if (isLocaleEnglish) {
                    LocaleHelper.changeLocale(this@SettingActivity, "en")
                    indoLocaleSwitch.isChecked = true
                } else {
                    LocaleHelper.changeLocale(this@SettingActivity, "in")
                    indoLocaleSwitch.isChecked = false
                }
            }
            indoLocaleSwitch.setOnCheckedChangeListener { _, isLocaleEnglish: Boolean ->
                settingViewModel.saveLocaleSetting(isLocaleEnglish)
            }

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()}
        }
        return super.onOptionsItemSelected(item)
    }
}