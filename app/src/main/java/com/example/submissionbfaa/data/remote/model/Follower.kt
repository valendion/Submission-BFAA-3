package com.example.submissionbfaa.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Follower(
    @Json(name = "login")
    var login: String,

    @Json(name = "avatar_url")
    var avaterUrl: String
)