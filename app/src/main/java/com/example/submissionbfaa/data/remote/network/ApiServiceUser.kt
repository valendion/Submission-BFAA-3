package com.example.submissionbfaa.data.remote.network


import com.example.submissionbfaa.data.remote.model.Follower
import com.example.submissionbfaa.data.remote.model.ItemSearch
import com.example.submissionbfaa.data.remote.model.User
import retrofit2.http.*

interface ApiServiceUser {

    @GET("users")
    suspend fun getUserGithub(): MutableList<User>

    @GET("search/users?q=username")
    suspend fun getUserSearch(@Query("q") username: String): ItemSearch

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): User

    @GET("users/{username}/followers")
    suspend fun getUserFollower(@Path("username") username: String): MutableList<Follower>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): MutableList<Follower>
}
