package com.example.submissionbfaa.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.submissionbfaa.data.local.entity.UserEntity

interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsersGithub(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE isFavorite = 1")
    fun getUsersFavorite(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(news: List<UserEntity>)

    @Update
    suspend fun updateUser(news: UserEntity)

    @Query("DELETE FROM user WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND isFavorite = 1)")
    suspend fun isNewsBookmarked(login: String): Boolean

}