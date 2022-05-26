package com.ajgroup.themoviedbnew.ui.opener

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.databinding.FragmentOpenerBinding
import com.ajgroup.themoviedbnew.repository.VerifRepository
import com.ajgroup.themoviedbnew.ui.verif.VerifViewModel
import com.ajgroup.themoviedbnew.ui.verif.VerifViewModelFactory
import org.koin.androidx.viewmodel.ext.android.viewModel


class OpenerFragment : Fragment() {
    private var _binding: FragmentOpenerBinding? = null
    private val binding get() = _binding!!

//    private val verifViewModel: VerifViewModel by viewModels {
//        VerifViewModelFactory(
//            VerifRepository(
//                UserDatabase.getInstance(
//            requireContext())!!.userDao(),
//            UserDataStoreManager(requireContext())
//        )
//        )
//    }
    private val verifViewModel: VerifViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentOpenerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifViewModel.emailPreference.observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                if (it == "") {
                    val action = OpenerFragmentDirections.actionOpenerFragmentToLoginFragment()
                    findNavController().navigate(action)
                } else {
                    val action = OpenerFragmentDirections.actionOpenerFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }, 2000)
        }
    }
}