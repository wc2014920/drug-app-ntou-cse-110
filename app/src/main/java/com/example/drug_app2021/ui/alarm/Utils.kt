package com.example.drug_app2021.ui.alarm

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.Services.bootservice
import com.example.drug_app2021.ui.AppAlarmReceiver
import kotlinx.coroutines.flow.first
import java.util.*

class Utils {
    companion object{
        fun setDrugAlarmRoutine(context: Context, alarmMgr: AlarmManager?, alarmIntent: PendingIntent?) {
            /**
             *
             * 每一小時運作一次
             */
            var c: Calendar = Calendar.getInstance();
            var hour = 0
            val time_min = c.get(Calendar.MINUTE)
            if(time_min<30){
                hour = c.get(Calendar.HOUR_OF_DAY);//9:24 -> 9:30
            }else{
                hour = c.get(Calendar.HOUR_OF_DAY) + 1;//9:34 -> 10:30
            }
            val min = 30
            c.setTimeInMillis(System.currentTimeMillis()); c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
            if (hour < c.get(Calendar.HOUR_OF_DAY)) {
                c.add(Calendar.DAY_OF_YEAR, 1)
            }
            if (hour == c.get(Calendar.HOUR_OF_DAY)) {
                if (min < c.get(Calendar.MINUTE)) {
                    c.add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            val time_sub=c.timeInMillis - System.currentTimeMillis()
            alarmMgr!!.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + time_sub,
                alarmIntent
            )
            Log.d("alarmMgr", "Repeating setDrugAlarm")
            Log.d("alarmMgr", "c:"+time_sub.toString())
            Log.d("alarmMgr", "s:"+System.currentTimeMillis().toString())
        }

        fun setAlarmRoutine(context: Context, alarmMgr: AlarmManager?, alarmIntent: PendingIntent?) {//5秒後設立，之後每1分鐘一次
            alarmMgr!!.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 60000,
                alarmIntent
            )
            Log.d("alarmMgr", "Repeating Mission")
        }

        fun isMyServiceRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (bootservice::class.java.getName() == service.service.className) {
                    return true
                }
            }
            return false
        }
    }
}