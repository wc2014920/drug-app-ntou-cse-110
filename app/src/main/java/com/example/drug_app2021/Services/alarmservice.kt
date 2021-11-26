package com.example.drug_app2021.Services

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.drug_app2021.MainActivity
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.R
import com.example.drug_app2021.data.UserPreferences
import com.example.drug_app2021.data.db.AppDatabase
import com.example.drug_app2021.data.network.RemoteDataSource
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class alarmservice : Service()  {

    val TAG = "AppService"
    private val remoteDataSouce = RemoteDataSource()
    private val appDatabase = AppDatabase
    private lateinit var userPreferences: UserPreferences
    private val CHANNEL_ID = "Your_ID"
    private val UPDATE_NOTIFICATION_ID = 123
    init {
        Log.d(TAG, "Service is running...")
    }
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    //    var i = 0
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //doSomething
        scope.launch{
            showNotification()
            AlarmDatabase()
        }
        return START_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            0
        )
        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Alarm Service")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(UPDATE_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(){
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "My_Service_Channel",
            NotificationManager.IMPORTANCE_LOW//不發出聲音
        )
        val mannger = getSystemService(
            NotificationManager::class.java
        )
        mannger.createNotificationChannel(serviceChannel)

    }

    private suspend fun AlarmDatabase() {

        /**
         * 發送
         * alarmanager signal -> 打開 peddind intent
         * */
        userPreferences = UserPreferences(MyApplication.context)
        val token = userPreferences.authToken.first()
        if( null != token ) {
            val api = remoteDataSouce.buildApi(UserApi::class.java, token)
            val db = appDatabase.getDatabase(MyApplication.context)
            val repository = UserRepository(api,db)
            val Retorfit = repository.readAlarmListItemAll() //下載
            Log.d(TAG, Retorfit.toString())
//            Retorfit.let { it ->
//
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}