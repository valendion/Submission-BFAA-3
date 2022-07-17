package com.example.submissionbfaa.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.submissionbfaa.ui.setting_activity.SettingDataStore
import com.example.submissionbfaa.ui.setting_activity.SettingViewModel
import com.example.submissionbfaa.ui.setting_activity.SettingViewModelFactory
import com.example.submissionbfaa.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideProtoData(get())}
    single { SettingDataStore(get()) }
    single { SettingViewModelFactory(get()) }
    single { SettingViewModel(get()) }
}

fun provideProtoData(appContext: Context): DataStore<Preferences>{
    return PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(appContext,Constant.SETTING_PREFERENCE)),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(Constant.SETTING_PREFERENCE) }
    )
}