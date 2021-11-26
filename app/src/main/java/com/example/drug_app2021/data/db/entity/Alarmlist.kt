package com.example.drug_app2021.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarmlist")
data class Alarmlist(
    @PrimaryKey(autoGenerate = false)
    val id: Int ?= null,
    val clinic: String ?= null,
    val clinic_id: Long? = null,
    val created_at: String ?= null,
    val doctor_id: Int ?= null,
    val doctor_name: String ?= null,
    val expired_date: String ?= null,
    val patient_id: Int ?= null,
    val patient_name: String ?= null,
    val updated_at: String ?= null
)