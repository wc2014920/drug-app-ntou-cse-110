package com.example.drug_app2021.ui

import android.app.Activity
import android.content.Intent
import android.hardware.camera2.CaptureFailure
import android.view.View
import androidx.fragment.app.Fragment
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.ui.auth.LoginFragment
import com.example.drug_app2021.ui.auth.SignupFragment
import com.example.drug_app2021.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean){
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean){

    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let{
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when{
        failure.isNetworkError -> requireView().snackbar("Please check your internet connection", retry)

        failure.errorCode == 400 -> {
            when(this){
                is LoginFragment -> {
                    requireView().snackbar("You've entered incorrect email or password")
                }
                is SignupFragment -> {
                    requireView().snackbar("Please check your registration field")
                }
                else -> {
                    (this as BaseFragment<*, *, *>).logout()
                }
            }
        }

        failure.errorCode == 401 -> {
            if(this is LoginFragment){
                requireView().snackbar("You've not verified your email yet")
            }else{
                (this as BaseFragment<*, *, *>).logout()
            }
        }

        failure.errorCode == 403 -> {
            if(this is LoginFragment){
                requireView().snackbar("Illegal access")
            }else{
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar("Illegal request")
        }

    }
}