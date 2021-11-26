package com.example.drug_app2021.ui.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.drug_app2021.R
import com.example.drug_app2021.data.response.Data
import com.example.drug_app2021.databinding.AdapterAlarmlistBinding

class AlarmlistAdapter(
    private val alarmlist: List<Data>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<AlarmlistAdapter.AlarmlistViewHolder>(){

    override fun getItemCount(): Int = alarmlist.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlarmlistViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_alarmlist,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AlarmlistViewHolder, position: Int) {
        holder.adapterAlarmlistBinding.textViewTitle.text = "鬧鐘 ${position +1 }"
        holder.adapterAlarmlistBinding.alarmlistitem = alarmlist[position]

        holder.adapterAlarmlistBinding.btncheckdetail.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.adapterAlarmlistBinding.btncheckdetail, alarmlist[position])
        }
    }

    inner class AlarmlistViewHolder(
        val adapterAlarmlistBinding: AdapterAlarmlistBinding
    ) : RecyclerView.ViewHolder(adapterAlarmlistBinding.root)
}