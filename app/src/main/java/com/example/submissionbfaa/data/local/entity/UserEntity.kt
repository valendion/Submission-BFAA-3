package com.example.submissionbfaa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String,

//    @ColumnInfo(name = "name")
//    var name: String,
//
//    @ColumnInfo(name = "company")
//    var company: String,
//
//    @ColumnInfo(name = "location")
//    var location: String,
//
//    @ColumnInfo(name = "followers")
//    var followers: Int,
//
//    @ColumnInfo(name = "following")
//    var following: Int,
//
    @ColumnInfo(name = "isFavorite")
    var isMarked: Boolean,
)