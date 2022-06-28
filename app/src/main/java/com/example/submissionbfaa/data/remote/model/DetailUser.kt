package com.example.submissionbfaa.data.remote.model

import com.squareup.moshi.Json

data class DetailUser(
    @Json(name = "login")
    var login: String,

    @Json(name = "id")
    var id: Int,

    @Json(name = "avatar_url")
    var avatarUrl: String,

    @Json(name = "name")
    var name: String,

    @Json(name = "company")
    var company: String,

    @Json(name = "location")
    var location: String,

    @Json(name = "public_repos")
    var publicRepos: Int,

    @Json(name = "followers")
    var followers: Int,

    @Json(name = "following")
    var following: Int

)