package com.example.drug_app2021.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.R
import com.example.drug_app2021.data.network.Authapi
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.repository.AuthRepository
import com.example.drug_app2021.databinding.FragmentLoginBinding
import com.example.drug_app2021.databinding.FragmentSignupBinding
import com.example.drug_app2021.ui.*
import com.example.drug_app2021.ui.base.BaseFragment
import com.example.drug_app2021.ui.home.HomeActivity
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : BaseFragment<AuthViewModel, FragmentSignupBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated( view, savedInstanceState )

        binding.progressBar3.visible(false)
        binding.btnRegister.enable(false)

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar3.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        requireView().snackbar("Register Success")
                        requireActivity().startNewActivity(AuthActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { register() }
            }
        })
        binding.password.addTextChangedListener{
            val name = binding.Username.text.toString().trim()
            val email = binding.Email.text.toString().trim()
            val phone = binding.Phone.text.toString().trim()
            binding.btnRegister.enable(phone.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && it.toString().isNotEmpty())

        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.textViewtologin.setOnClickListener {
            val fragment = LoginFragment()
            val transction = fragmentManager?.beginTransaction()
            transction?.setCustomAnimations(R.anim.from_right,R.anim.to_left)
            transction?.replace(R.id.fragmentContainerView, fragment)?.commit()
        }

//        binding.textView.setOnClickListener {
//            Navigation.findNavController(binding.root).navigate(R.id.action_signupFragment_to_loginFragment2)
//        }
    }

    fun register() {
        val name = binding.Username.text.toString().trim()
        val email = binding.Email.text.toString().trim()
        val phone = binding.Phone.text.toString().trim()
        val password = binding.password.text.toString().trim()
        binding.progressBar3.visible(true)
        viewModel.register(name, email, password, phone)
    }

    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java


    override fun getFragmentRepository() =
        AuthRepository(remoteDataSouce.buildApi(Authapi::class.java), userPreferences,
            appDatabase.getDatabase(MyApplication.context))
    // TODO: Rename and change types of parameters

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentSignupBinding
            = FragmentSignupBinding.inflate(inflater, container, false)

}