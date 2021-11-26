package com.example.drug_app2021.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.drug_app2021.MyApplication
import com.example.drug_app2021.R
import com.example.drug_app2021.data.db.entity.User
import com.example.drug_app2021.databinding.FragmentLoginBinding
import com.example.drug_app2021.data.network.Authapi
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.repository.AuthRepository
import com.example.drug_app2021.ui.base.BaseFragment
import com.example.drug_app2021.ui.enable
import com.example.drug_app2021.ui.handleApiError
import com.example.drug_app2021.ui.home.HomeActivity
import com.example.drug_app2021.ui.startNewActivity
import com.example.drug_app2021.ui.visible
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated( view, savedInstanceState )
        binding.progressBar.visible(false)
        binding.btnlogin.enable(false)
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.token)
                        insertUserDatatoDatabase(it.value.user)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
            }
        })
        binding.password.addTextChangedListener{
            val email = binding.email.text.toString().trim()
            binding.btnlogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())

        }
        binding.btnlogin.setOnClickListener {
            login()
        }
        binding.textViewtosignup.setOnClickListener {
            val fragment = SignupFragment()
            val transction = fragmentManager?.beginTransaction()
            transction?.setCustomAnimations(R.anim.from_left,R.anim.to_right)
            transction?.replace(R.id.fragmentContainerView, fragment)?.commit()
        }


    }
    private suspend fun insertUserDatatoDatabase(it: com.example.drug_app2021.data.response.User) {
        val user = User(null,it.id,it.cellphone,it.email,it.name,it.status,it.token)
        viewModel.saveUser(user)
    }


    private fun login() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        binding.progressBar.visible(true)
        viewModel.login(email, password)
    }
    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java
    override fun getFragmentRepository() =
        AuthRepository(remoteDataSouce.buildApi(Authapi::class.java), userPreferences
            , appDatabase.getDatabase(MyApplication.context))

//    override fun getFragmentView() = R.layout.fragment_login
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentLoginBinding
        = FragmentLoginBinding.inflate(inflater, container, false)
}
