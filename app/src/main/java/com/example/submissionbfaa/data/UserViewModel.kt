package com.example.submissionbfaa.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.utils.CoroutineHelper
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserGithub() = userRepository.getUserGithub()
    fun getFavoriteUser() = userRepository.getFavoriteUser()

    fun saveUser(userEntity: UserEntity) {
        viewModelScope.launch {
            CoroutineHelper.oi { userRepository.setUserMarked(userEntity, true) }
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch {
            CoroutineHelper.oi { userRepository.setUserMarked(userEntity, false) }
        }
    }

    fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    fun getFollower(username: String)  = userRepository.getFollower(username)

    fun getFollowing(username: String) =  userRepository.getFollowing(username)

    fun getSearch(username: String) =  userRepository.getSearch(username)

}