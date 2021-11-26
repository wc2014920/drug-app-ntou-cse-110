package com.example.drug_app2021.ui.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.data.UserPreferences
import com.example.drug_app2021.data.db.AppDatabase
import com.example.drug_app2021.data.network.RemoteDataSource
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.repository.BaseRepository
import com.example.drug_app2021.ui.AppAlarmReceiver
import com.example.drug_app2021.ui.DrugAlarmReceiver
import com.example.drug_app2021.ui.auth.AuthActivity
import com.example.drug_app2021.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepository> : Fragment(){

    companion object{
        var alarmMgr1: AlarmManager? = null
        lateinit var alarmIntent1: PendingIntent
        var alarmMgr2: AlarmManager? = null
        lateinit var alarmIntent2: PendingIntent
        val REQUEST_TIMER1 = 1
        val REQUEST_TIMER2 = 2
    }
    protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSouce = RemoteDataSource()
    protected val appDatabase = AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container, false)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch {
            userPreferences.authToken.first()
        }
        return binding.root
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSouce.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        userPreferences.clear()
        alarmMgr1?.cancel(alarmIntent1)
        alarmMgr2?.cancel(alarmIntent2)
        Log.d("alarmMgr","CANCEL")
        requireActivity().startNewActivity(AuthActivity::class.java)
    }
    fun setAlarm() {//5秒後設立，之後每1分鐘一次
        alarmMgr1 = MyApplication.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent: Intent = Intent()
        intent.setClass(MyApplication.context, AppAlarmReceiver::class.java)
        intent.action = "RepeatAction"
        alarmIntent1 = PendingIntent.getBroadcast(MyApplication.context,REQUEST_TIMER1,intent,0)
        alarmMgr1?.cancel(alarmIntent1)
        alarmMgr1!!.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 5*1000,
            alarmIntent1
        )
        Log.d("alarmMgr","setAlarm AlarmManager")

        /**10秒後執行，每一小時運作一次*/
        alarmMgr2 = MyApplication.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent2: Intent = Intent()
        intent2.setClass(MyApplication.context, DrugAlarmReceiver::class.java)
        intent2.action = "DrugAlarm"
        alarmIntent2 = PendingIntent.getBroadcast(MyApplication.context, REQUEST_TIMER2, intent2, 0)
        alarmMgr2?.cancel(alarmIntent2)
        alarmMgr2!!.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 10000,
            alarmIntent2
        ); Log.d("alarmMgr", "setDrugAlarm AlarmManager")
    }


    abstract fun getViewModel() : Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean) : B

    abstract fun getFragmentRepository(): R
}
