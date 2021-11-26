package com.example.drug_app2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.drug_app2021.data.UserPreferences
import com.example.drug_app2021.ui.auth.AuthActivity
import com.example.drug_app2021.databinding.ActivityMainBinding
import com.example.drug_app2021.ui.auth.SignupFragment
import com.example.drug_app2021.ui.home.HomeActivity
import com.example.drug_app2021.ui.startNewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences = UserPreferences(this@MainActivity)

        userPreferences.authToken.asLiveData().observe(this, Observer {
            Log.d("userPreferences.authToken",it.toString())
            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
    }

}