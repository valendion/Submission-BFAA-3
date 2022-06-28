package com.example.submissionbfaa.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionbfaa.data.local.entity.UserEntity
import org.koin.dsl.module

val databaseModule = module {
    //Provide UserDatabase
    single {
        Room.databaseBuilder(get(), UserDatabase::class.java, UserDatabase.USER_DATABASE_NAME)
            .build()
    }

    //Provide UserDao
    single {get<UserDatabase>().userDao() }

}



@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val USER_DATABASE_NAME = "User.db"
    }
}