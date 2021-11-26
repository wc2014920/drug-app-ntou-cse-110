package com.example.drug_app2021.ui.home.data

import android.view.View
import com.example.drug_app2021.data.response.Data

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, alarmlistitem: Data)
}