package com.example.drug_app2021.data.response

data class Detail(
    val created_at: String,
    val day: String,
    val drug_amount: String,
    val drug_code: String,
    val drug_name: String,
    val drug_pic_url: String,
    val id: Int,
    val patient_id: Int,
    val prescription_id: Int,
    val time_morning: String,
    val time_night: String,
    val time_noon: String,
    val time_sleep: String,
    val updated_at: String
)