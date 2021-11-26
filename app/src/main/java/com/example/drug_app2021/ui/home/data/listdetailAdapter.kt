package com.example.drug_app2021.ui.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.drug_app2021.R
import com.example.drug_app2021.data.db.entity.Alarmlistitem
import com.example.drug_app2021.databinding.AdapterListdetailBinding
import com.example.drug_app2021.ui.loadImage

class listdetailAdapter(
    private val detaillist: List<Alarmlistitem>,
) : RecyclerView.Adapter<listdetailAdapter.ListdetailViewHolder>(){
    inner class ListdetailViewHolder(
        val adapterListdetailBinding: AdapterListdetailBinding
    ) : RecyclerView.ViewHolder(adapterListdetailBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListdetailViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_listdetail,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListdetailViewHolder, position: Int) {
        holder.adapterListdetailBinding.listitemdetail = detaillist[position]
        //img 處理
        loadImage(holder.adapterListdetailBinding.drugPic, detaillist[position].drug_pic_url.toString())
        //...
    }

    override fun getItemCount(): Int = detaillist.size
}