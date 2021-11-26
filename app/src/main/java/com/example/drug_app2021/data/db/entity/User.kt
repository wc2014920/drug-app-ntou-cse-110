package com.example.drug_app2021.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_USER_ID = 1

@Entity(tableName = "user")//table
data class User(
    @PrimaryKey(autoGenerate = false) val uid: Int? = 1,
    @ColumnInfo(name = "userid") var id: Int ?= 1,
    @ColumnInfo(name = "userphone") var cellphone: String ?= null,
    @ColumnInfo(name = "useremail") var email: String ?= null,
    @ColumnInfo(name = "username") var name: String ?= null,
    @ColumnInfo(name = "status") var status: String ?= null,
    @ColumnInfo(name = "api_token") var token: String ?= null
)