package com.example.drug_app2021.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.drug_app2021.Services.alarmservice
import com.example.drug_app2021.ui.alarm.Utils
import com.example.drug_app2021.ui.alarm.Utils.Companion.setDrugAlarmRoutine
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmIntent2
import com.example.drug_app2021.ui.base.BaseFragment.Companion.alarmMgr2

class DrugAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("alarmMgr","123")
            if (intent?.action == "DrugAlarm") {
                Log.d("alarmMgr", "DrugAlarmReceiver")
                if (context != null) {
                    val serviceIntent = Intent(context, alarmservice::class.java).also {
                        if (Utils.isMyServiceRunning(context)) {
                            context.stopService(it)
                        }
                        context.startForegroundService(it)
                    };
                    setDrugAlarmRoutine(context, alarmMgr2, alarmIntent2)
                }
            }
    }

}