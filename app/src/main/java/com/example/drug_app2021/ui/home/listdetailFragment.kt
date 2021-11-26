package com.example.drug_app2021.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.data.db.entity.Alarmlistitem
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.repository.UserRepository
import com.example.drug_app2021.databinding.FragmentListdetailBinding
import com.example.drug_app2021.ui.base.BaseFragment
import com.example.drug_app2021.ui.home.data.listdetailAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class listdetailFragment : BaseFragment<HomeViewModel, FragmentListdetailBinding, UserRepository>() {

    private var getID: String? = ""
    private lateinit var listdata: List<Alarmlistitem>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getID = arguments?.getString("messageID")
        Log.d("messageID","${getID}")
        updateUI()

    }
    private fun updateUI() {
        lifecycleScope.launch {
            listdata = viewModel.readAlarmlistitem(getID.toString())
            Log.d("listdata","${listdata}")
            binding.recyclerViewDetaillist.also {
                it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                it.setHasFixedSize(true)
                it.adapter = listdetailAdapter(listdata)
            }
        }
    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentListdetailBinding
            = FragmentListdetailBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSouce.buildApi(UserApi::class.java, token)
        return UserRepository(api, appDatabase.getDatabase(MyApplication.context))
    }

}