package com.example.submissionbfaa.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.data.remote.model.Follower
import com.example.submissionbfaa.utils.CoroutineHelper
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _usernameClick = MutableLiveData<String>()
    val usernameClick: LiveData<String> get() = _usernameClick

    fun setUsernameClick(username: String) {
        _usernameClick.value = username
    }

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

}