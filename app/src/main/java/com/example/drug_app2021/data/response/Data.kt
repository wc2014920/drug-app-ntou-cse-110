package com.example.drug_app2021.data.response

data class Data(
    val clinic: String,
    val clinic_id: Long,
    val created_at: String,
    val doctor_id: Int,
    val doctor_name: String,
    val expired_date: String,
    val id: Int,
    val patient_id: Int,
    val patient_name: String,
    val updated_at: String
)