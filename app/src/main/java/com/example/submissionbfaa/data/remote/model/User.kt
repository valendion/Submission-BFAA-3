package com.example.submissionbfaa.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "login")
    var login: String,

    @Json(name = "id")
    var id: Int,

    @Json(name = "avatar_url")
    var avatarUrl: String,
)