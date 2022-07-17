package com.example.submissionbfaa.ui.setting_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModelFactory(private val settingDataStore: SettingDataStore):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(settingDataStore) as T
        }
        throw IllegalAccessException("Unknown ViewModel class : ${modelClass.name}")
    }
}