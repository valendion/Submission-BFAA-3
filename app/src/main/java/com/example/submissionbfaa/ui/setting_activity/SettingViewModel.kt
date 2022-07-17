package com.example.submissionbfaa.ui.setting_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingDataStore): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }
}