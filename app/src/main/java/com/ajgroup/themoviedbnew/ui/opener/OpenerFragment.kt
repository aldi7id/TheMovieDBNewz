package com.ajgroup.themoviedbnew.ui.opener

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.databinding.FragmentOpenerBinding


class OpenerFragment : Fragment() {
    private var _binding: FragmentOpenerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentOpenerBinding.inflate(inflater, container, false)
        Handler(Looper.getMainLooper()).postDelayed({
            val login = 0
            if (login == 0) {
                val action = OpenerFragmentDirections.actionOpenerFragmentToLoginFragment()
                findNavController().navigate(action)
            } else {
                val action = OpenerFragmentDirections.actionOpenerFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }, 2000)
        return binding.root
    }
}