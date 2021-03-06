package com.example.submissionbfaa.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
@JsonClass(generateAdapter = true)
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String = "",

    @ColumnInfo(name = "isFavorite")
    var isMarked: Boolean = false,
): Parcelable
