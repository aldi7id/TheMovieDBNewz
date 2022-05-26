package com.ajgroup.themoviedbnew.ui.verif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.data.local.model.User
import com.ajgroup.themoviedbnew.databinding.FragmentRegisterBinding
import com.ajgroup.themoviedbnew.repository.VerifRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val verifViewModel by viewModels<VerifViewModel>{
        VerifViewModelFactory(VerifRepository(UserDatabase.getInstance(requireContext())!!.userDao()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            if ((binding.etPassword.text.toString() == binding.etPassword2.text.toString())) {

                val email = binding.etEmail.text.toString()
                val nama = binding.etName.text.toString()
                val password = binding.etPassword.text.toString()


                val user = User(null, nama, email, password)

                lifecycleScope.launch(Dispatchers.IO) {

                    val isEmailExist = verifViewModel.checkEmail(email)

                    activity?.runOnUiThread {
                        if (isEmailExist == null) {
                            registerUser(user)
                        } else {
                            Toast.makeText(context, getString(R.string.email_registered), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
            }

        }
    }

    private fun registerUser(user: User) {
        lifecycleScope.launch(Dispatchers.IO){
            val userIsRegister = verifViewModel.register(user)

            activity?.runOnUiThread {
                if (userIsRegister == (0).toLong()){
                    Toast.makeText(context, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}