package com.example.submissionbfaa.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailUser(
    @Json(name = "login")
    var login: String? = null,

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "avatar_url")
    var avatarUrl: String? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "company")
    var company: String? = null,

    @Json(name = "location")
    var location: String? = null,

    @Json(name = "public_repos")
    var publicRepos: Int? = null,

    @Json(name = "followers")
    var followers: Int? = null,

    @Json(name = "following")
    var following: Int? = null

)