package com.example.drug_app2021.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.drug_app2021.R
import com.example.drug_app2021.ui.auth.LoginFragment

class HomeActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView2, HomeFragment())
            .commit()


    }
    override fun passDataTypeString(SendData: String) {
        //HomeFragment -> listdetailFragment
        val bundle = Bundle()
        bundle.putString("messageID", SendData)
        val transaction = this.supportFragmentManager.beginTransaction()
        val listdetailFragment = listdetailFragment()
        listdetailFragment.arguments = bundle
        Log.d("bundle",bundle.toString())
        transaction
            .setCustomAnimations(R.anim.from_left, R.anim.to_right, R.anim.from_right, R.anim.to_left)
            .addToBackStack(null)
            .replace(R.id.fragmentContainerView2,
                listdetailFragment
            ).commit()
    }
}