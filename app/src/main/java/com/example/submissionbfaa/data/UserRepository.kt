package com.example.submissionbfaa.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.data.local.room.UserDao
import com.example.submissionbfaa.data.remote.network.ApiServiceUser
import com.example.submissionbfaa.utils.Status

class UserRepository(
    private val apiServiceUser: ApiServiceUser,
    private val userDao: UserDao
) {
    fun getUserGithub(): LiveData<Status<List<UserEntity>>>{
        return liveData {

            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserGithub()
                val userList = response.map {
                    val isMarked = userDao.isNewsBookmarked(it.login)
                    UserEntity(
                        it.login,
                        it.avatarUrl,
                        it.name,
                        it.company,
                        it.location,
                        it.followers,
                        it.following,
                        isMarked
                    )
                }
                userDao.deleteAll()
                userDao.insertUser(userList)

            }catch (e: Exception){
                Log.d("UserRepository", "getUserGithub: ${e.message.toString()}")
                emit(Status.Error(e.message.toString()))
            }

            val localData: LiveData<Status<List<UserEntity>>> =  userDao.getUsersGithub().map {
                Status.Success(it)
            }

            emitSource(localData)
        }
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>>{
        return userDao.getUsersFavorite()
    }

    suspend fun setUserMarked(userEntity: UserEntity, userMarkState: Boolean){
        userEntity.isMarked = userMarkState
        userDao.updateUser(userEntity)
    }
}