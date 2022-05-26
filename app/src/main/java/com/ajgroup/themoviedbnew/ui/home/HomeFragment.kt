package com.ajgroup.themoviedbnew.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.api.model.Result
import com.ajgroup.themoviedbnew.data.api.ApiClient
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.databinding.FragmentHomeBinding
import com.ajgroup.themoviedbnew.repository.HomeRepository
import com.ajgroup.themoviedbnew.ui.adapter.HomeAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepository(
                ApiClient.instance, UserDataStoreManager(requireContext())
            ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivFavorite.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        binding.ivProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        homeViewModel.namaPreference.observe(viewLifecycleOwner){
            if (it!=""){
                binding.welcome.text = "Welcome,\n$it!"
            }
        }

        homeViewModel.discoveryMovies.observe(viewLifecycleOwner){
            showDiscoveryMovies(it.results)
        }
    }

    private fun showDiscoveryMovies(results: List<Result>?) {
        val discoveryAdapter = HomeAdapter{
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
            findNavController().navigate(action)
        }
        discoveryAdapter.submitList(results)
        binding.rvList.adapter = discoveryAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}