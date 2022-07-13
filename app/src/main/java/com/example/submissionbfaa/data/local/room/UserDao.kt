package com.example.submissionbfaa.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submissionbfaa.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsersGithub(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE isFavorite = 1")
    fun getUsersFavorite(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM user WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND isFavorite = 1)")
    suspend fun isUserBookmarked(login: String): Boolean

//    @Query("SELECT EXISTS(SELECT * FROM user WHERE isFavorite = 1)")
//    suspend fun isUserBookmarked(): Boolean
}