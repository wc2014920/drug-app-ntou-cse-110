package com.example.drug_app2021.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.Services.bootservice
import kotlinx.coroutines.*
import com.example.drug_app2021.data.UserPreferences
import com.example.drug_app2021.ui.alarm.Utils.Companion.isMyServiceRunning
import com.example.drug_app2021.ui.alarm.Utils.Companion.setAlarmRoutine
import com.example.drug_app2021.ui.alarm.Utils.Companion.setDrugAlarmRoutine
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmIntent1
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmIntent2
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmMgr1
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmMgr2
import kotlinx.coroutines.flow.first

class AppAlarmReceiver : BroadcastReceiver() {
//    private var alarmMgr: AlarmManager? = null
//    private var alarmMgr2: AlarmManager? = null
//    lateinit var alarmIntent: PendingIntent
//    lateinit var alarmIntent2: PendingIntent
//    private val REQUEST_TIMER1 = 1
//    private val REQUEST_TIMER2 = 2
    private lateinit var userPreferences: UserPreferences

    @DelicateCoroutinesApi
    override fun onReceive(context: Context, intent: Intent?) {
        // 當收到訊號做以下的任務
        goAsync(GlobalScope,  Dispatchers.IO){
            userPreferences = UserPreferences(MyApplication.context)
            val token = userPreferences.authToken.first()
            if (Intent.ACTION_BOOT_COMPLETED == intent?.action && null != token) {//開機 一次任務
                Log.d("alarmMgr", "AppAlarmReceiver ACTION_BOOT_COMPLETED")
                setDrugAlarmRoutine(context, alarmMgr2, alarmIntent2)
                setAlarmRoutine(context, alarmMgr1, alarmIntent1) }
            Log.d("alarmMgr","action = "+intent?.action.toString())
            if (intent?.action == "RepeatAction") { //登入 APP
                Log.d("alarmMgr", "AppAlarmReceiver RepeatAction")
                val serviceIntent = Intent(context, bootservice::class.java).also {
                    if (isMyServiceRunning(context)) {
                        context.stopService(it)
                    }
                    it.putExtra("token",token)
                    context.startForegroundService(it)
                };
                setAlarmRoutine(context, alarmMgr1, alarmIntent1)
            }
        }
    }

    fun BroadcastReceiver.goAsync(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        block: suspend () -> Unit
    ) {
        val pendingResult = goAsync()
        coroutineScope.launch(dispatcher) {
            block()
            pendingResult.finish()
        }
    }


}