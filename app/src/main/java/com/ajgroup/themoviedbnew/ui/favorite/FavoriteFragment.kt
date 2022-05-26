package com.ajgroup.themoviedbnew.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.databinding.FragmentFavoriteBinding
import com.ajgroup.themoviedbnew.repository.FavoriteRepository
import com.ajgroup.themoviedbnew.ui.adapter.FavoriteAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory(
            FavoriteRepository(
                UserDatabase.getInstance(requireContext())!!.favoriteDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner){
            showListFavorites(it)
        }
        favoriteViewModel.getAllFavorites()
    }

    private fun showListFavorites(list: List<Favorite?>?) {
        val adapter= FavoriteAdapter {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieFragment(it.id!!)
            findNavController().navigate(action)
        }
        adapter.submitList(list)
        binding?.rvFavorite?.adapter = adapter

    }

}