package com.example.drug_app2021.ui.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.data.db.entity.Alarmlistitem
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.repository.UserRepository
import com.example.drug_app2021.data.response.Data
import com.example.drug_app2021.data.response.Detail
import com.example.drug_app2021.databinding.FragmentHomeBinding
import com.example.drug_app2021.ui.base.BaseFragment
import com.example.drug_app2021.ui.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.math.max
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drug_app2021.R
import com.example.drug_app2021.ui.AppAlarmReceiver
import com.example.drug_app2021.ui.auth.SignupFragment

import com.example.drug_app2021.ui.home.data.AlarmlistAdapter
import com.example.drug_app2021.ui.home.data.RecyclerViewClickListener
import kotlinx.android.synthetic.main.adapter_alarmlist.*
import java.util.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>(), RecyclerViewClickListener {

    private lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar2.visible(true)
        binding.STATUS.visible(true)
        binding.refreshLayout.isRefreshing = false
        init()
    }
    private fun init() {
        lifecycleScope.launch{
            update()
            binding.btnlogout.setOnClickListener { logout() }
            binding.refreshLayout.setOnRefreshListener{ binding.refreshLayout.isRefreshing = true; update() }
            setAlarm() // 在 BaseFragment()
        }
    }

    private fun update() {
        viewModel.getAlarmList()
        viewModel.alarmList.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch{
                        binding.refreshLayout.isRefreshing = false
                        binding.progressBar2.visible(false)
                        if (null != response.value){
                            binding.STATUS.visible(false)
                            binding.recyclerViewAlarmlist.also {
                                it.layoutManager = LinearLayoutManager(requireContext())
                                it.setHasFixedSize(true)
                                it.adapter = AlarmlistAdapter(response.value.data, this@HomeFragment)
                            }
                            // 透由 [網路] 更新 -> Insert 至 DB
                            viewModel.insertAlarmlisttoDB(response.value.data, response.value.detail)
                        }else{
                            binding.STATUS.text="你還未擁有\n藥物鬧鐘"
                        }
                    }
                }
                is Resource.Loading -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.progressBar2.visible(true)
                    binding.STATUS.visible(true)
                }
                else -> {
                    viewModel.readAlarmList.observe(viewLifecycleOwner, Observer {
                        lifecycleScope.launch{
                            binding.refreshLayout.isRefreshing = false
                            binding.progressBar2.visible(false)
                            binding.STATUS.visible(false)
                            val toast = Toast.makeText(MyApplication.context, "未連接網路", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    })
                }
            }
        })
    }



    fun updateIO() {
        viewModel.getAlarmList()
        viewModel.alarmList.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch{
                        if (null != response.value){
                            // 透由 [網路] 更新 -> Insert 至 DB
                                viewModel.insertAlarmlisttoDB(response.value.data, response.value.detail)
                        }
                    }
                }
                is Resource.Loading -> {
                }
                else -> {
                    viewModel.readAlarmList.observe(viewLifecycleOwner, Observer {
                        lifecycleScope.launch{
                            val toast = Toast.makeText(MyApplication.context, "未連接網路", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    })
                }
            }
        })
    }



    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSouce.buildApi(UserApi::class.java, token)
        return UserRepository(api, appDatabase.getDatabase(MyApplication.context))
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding
            = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onRecyclerViewItemClick(view: View, alarmlistitem: Data) {
        when(view.id){
            R.id.btncheckdetail -> {
                val toast = Toast.makeText(requireContext(), "Checkdetail Button Clicked", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                val communicator = activity as Communicator
                Log.d("alarmlistitem.id", alarmlistitem.id.toString())
                communicator.passDataTypeString(alarmlistitem.id.toString())//傳送id值
            }
        }
    }
}