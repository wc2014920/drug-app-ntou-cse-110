package com.example.drug_app2021.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmlistitem")
data class Alarmlistitem(
    @PrimaryKey(autoGenerate = false)
    val id: Int ?= null,
    val prescription_id: Int ?= null,
    val patient_id: Int ?= null,
    val drug_name: String ?= null,
    val drug_pic_url: String ?= null,
    val drug_code: String ?= null,
    val drug_amount: String ?= null,
    val day: String ?= null,
    val time_morning: String ?= null,
    val time_night: String ?= null,
    val time_noon: String ?= null,
    val time_sleep: String ?= null,
    val created_at: String ?= null,
    val updated_at: String ?= null
)
