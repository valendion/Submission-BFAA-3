package com.example.submissionbfaa.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.data.local.room.UserDao
import com.example.submissionbfaa.data.remote.model.DetailUser
import com.example.submissionbfaa.data.remote.model.Follower
import com.example.submissionbfaa.data.remote.network.ApiServiceUser
import com.example.submissionbfaa.utils.Status

class UserRepository(
    private val apiServiceUser: ApiServiceUser,
    private val userDao: UserDao
) {

    fun getUserGithub(): LiveData<Status<List<UserEntity>>> {
        return liveData {

            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserGithub()
                val userList = response.map {
                    val isMarked = userDao.isUserBookmarked(it.login)
                    UserEntity(
                        it.login,
                        it.avatarUrl,
                        isMarked
                    )
                }
                userDao.deleteAll()
                userDao.insertUser(userList)

                val localData: LiveData<Status<List<UserEntity>>> = userDao.getUsersGithub().map {
                    Status.Success(it)
                }

                emitSource(localData)
            } catch (e: Exception) {
                Log.d("UserRepository", "getUserGithub: ${e.message.toString()}")
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getFavoriteUser(): LiveData<Status<List<UserEntity>>> {
        return liveData {
            emit(Status.Loading)

            try {
                val favoriteData: LiveData<Status<List<UserEntity>>> = userDao.getUsersFavorite().map {
                    Status.Success(it)
                }
                emitSource(favoriteData)
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    suspend fun setUserMarked(userEntity: UserEntity, userMarkState: Boolean) {
        userEntity.isMarked = userMarkState
        userDao.updateUser(userEntity)
    }

    fun getDetailUser(username: String): LiveData<Status<DetailUser>> {
        return liveData {
            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserDetail(username)
                emit(Status.Success(response))
            } catch (e: Exception) {
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getFollower(username: String): LiveData<Status<List<Follower>>>{
        return liveData {
            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserFollower(username)
                emit(Status.Success(response))
            }catch (e:  Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getFollowing(username: String): LiveData<Status<List<Follower>>>{
        return liveData {
            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserFollowing(username)
                emit(Status.Success(response))
            }catch (e:  Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getSearch(username: String): LiveData<Status<List<UserEntity>>>{
        return liveData {
            emit(Status.Loading)

            try {
                val response = apiServiceUser.getUserSearch(username)
                emit(Status.Success(response))
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }
}