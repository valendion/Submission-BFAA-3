package com.example.submissionbfaa.ui.setting_activity

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingDataStore(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val LOCALE_KEY = booleanPreferencesKey("locale_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { value: Preferences ->
            value[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean){
        dataStore.edit{value: MutablePreferences ->
            value[THEME_KEY] = isDarkMode
        }
    }

    fun getLocaleSetting(): Flow<Boolean> {
        return dataStore.data.map { value: Preferences ->
            value[LOCALE_KEY] ?: false
        }
    }

    suspend fun saveLocaleSetting(isLocaleEnglish: Boolean){
        dataStore.edit{value: MutablePreferences ->
            value[LOCALE_KEY] = isLocaleEnglish
        }
    }


}