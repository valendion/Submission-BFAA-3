package com.example.submissionbfaa.di

import android.app.Application
import com.example.submissionbfaa.data.UserRepository
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.data.local.room.databaseModule
import com.example.submissionbfaa.data.remote.network.networkModule
import com.example.submissionbfaa.ui.detail_activity.FollowAdapter
import com.example.submissionbfaa.ui.main_activity.UserAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {

    single {
        UserRepository(
            get(),
            get()
        )
    }

    factory { UserAdapter() }

    factory { FollowAdapter() }

    viewModel { UserViewModel(get()) }

    single { ViewModelFactory(get()) }
}

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, networkModule, databaseModule))
        }
    }
}