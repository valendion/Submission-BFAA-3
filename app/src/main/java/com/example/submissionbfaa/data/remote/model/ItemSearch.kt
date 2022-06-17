package com.example.submissionbfaa.data.remote.model

import com.squareup.moshi.Json


data class ItemSearch(
    @Json(name = "total_count")
    var totalCount: Int,

    @Json(name = "items")
    var items: MutableList<User>
)