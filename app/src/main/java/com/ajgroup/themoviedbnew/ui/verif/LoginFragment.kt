package com.ajgroup.themoviedbnew.ui.verif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.databinding.FragmentLoginBinding
import com.ajgroup.themoviedbnew.repository.VerifRepository
import com.ajgroup.themoviedbnew.ui.verif.LoginFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private val verifViewModel by viewModels<VerifViewModel>{
//        VerifViewModelFactory(VerifRepository(UserDatabase.getInstance(requireContext())!!.userDao(),
//        UserDataStoreManager(requireContext())
//            ))
//    }
    private val verifViewModel: VerifViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SignUpButton.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.toString().isNotEmpty() && binding.etPassword.toString().isNotEmpty()){
                val email = binding.etEmail.text.toString()
                val pass = binding.etPassword.text.toString()
                lifecycleScope.launch(Dispatchers.IO){
                    val isLogin = verifViewModel.login(email,pass)

                    activity?.runOnUiThread {
                        if (isLogin == null){
                            Toast.makeText(context, getString(R.string.email_pass_wrong), Toast.LENGTH_SHORT).show()
                        } else {
                            lifecycleScope.launch(Dispatchers.IO){
                                verifViewModel.setEmailPreference(email)
                                verifViewModel.setNamaPreference(isLogin.name)
                                runBlocking(Dispatchers.Main){
                                    val action = LoginFragmentDirections
                                        .actionLoginFragmentToHomeFragment()
                                    it.findNavController().navigate(action)
                                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}