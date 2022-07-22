package com.example.submissionbfaa.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemSearch(
    @Json(name = "total_count")
    var totalCount: Int,

    @Json(name = "items")
    var items: List<User>
)