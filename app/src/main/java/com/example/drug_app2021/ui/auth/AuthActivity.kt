package com.example.drug_app2021.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.drug_app2021.R
import com.example.drug_app2021.data.db.AppDatabase
import com.example.drug_app2021.data.network.Authapi
import com.example.drug_app2021.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,LoginFragment())
            .commit()

    }
}