package com.ajgroup.themoviedbnew.ui.verif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.databinding.FragmentLoginBinding
import com.ajgroup.themoviedbnew.ui.verif.LoginFragmentDirections

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private val verifViewModel by viewModels<VerifViewModel>{
//    }


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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}