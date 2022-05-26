package com.ajgroup.themoviedbnew.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.api.Status
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.databinding.FragmentDetailMovieBinding
import com.ajgroup.themoviedbnew.repository.DetailRepository
import com.ajgroup.themoviedbnew.ui.verif.VerifViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

//    private val detailMovieViewModel by viewModels<DetailMovieViewModel> {
//        DetailMovieViewModelFactory(
//            DetailRepository(
//                ApiClient.instance,
//                UserDatabase.getInstance(requireContext())!!.favoriteDao())
//        )
//    }
    private val detailMovieViewModel: DetailMovieViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        detailMovieViewModel.detailMovie.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.apply {
                        ivHeader.visibility = View.INVISIBLE
                        ivFavorite.visibility = View.INVISIBLE
                        tvDesc.visibility = View.INVISIBLE
                        tvGenre.visibility = View.INVISIBLE
                        tvJudul.visibility = View.INVISIBLE
                        tvRelease.visibility = View.INVISIBLE
                        tvStatus.visibility = View.INVISIBLE
                        tvTagLine.visibility = View.INVISIBLE
                    }
                    Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    binding.apply {
                        ivHeader.visibility = View.VISIBLE
                        ivFavorite.visibility = View.VISIBLE
                        tvDesc.visibility = View.VISIBLE
                        tvGenre.visibility = View.VISIBLE
                        tvJudul.visibility = View.VISIBLE
                        tvRelease.visibility = View.VISIBLE
                        tvStatus.visibility = View.VISIBLE
                        tvTagLine.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, getString(R.string.loaded), Toast.LENGTH_SHORT).show()
                    binding.apply {
                        tvJudul.text = getString(R.string.tv_tittle).plus(it.data?.title)
                        tvGenre.text = getString(R.string.tv_genres).plus(it.data?.genres?.get(0)?.name).plus("or").plus(
                            it.data?.genres?.get(1)?.name)
                        tvRelease.text = getString(R.string.tv_release_date).plus(it.data?.releaseDate)
                        tvTagLine.text = getString(R.string.tv_taglines).plus(it.data?.tagline)
                        tvStatus.text = getString(R.string.tv_status).plus(it.data?.status)
                        tvDesc.text = getString(R.string.tv_desc).plus(it.data?.overview)
                        Glide.with(requireContext()).load(IMAGE_BASE+it.data?.backdropPath).into(ivHeader)
                    }
                    binding.ivFavorite.setOnClickListener { _ ->
                        lifecycleScope.launch(Dispatchers.IO){
                            val isFavorite = detailMovieViewModel.getFavoriteById(movieId)
                            activity?.runOnUiThread {
                                if(isFavorite == null ){
                                    val newFavorite = Favorite(
                                        id = it.data?.id,
                                        releaseDate = it.data?.releaseDate?:"",
                                        posterPath = it.data?.posterPath,
                                        title = it.data?.title?:"",
                                        voteAverage = it.data?.voteAverage?:0.0
                                    )
                                    lifecycleScope.launch(Dispatchers.IO){
                                        detailMovieViewModel.addToFavorite(newFavorite)
                                        runBlocking(Dispatchers.Main){
                                            Toast.makeText(context, it.data?.title + "Success Add To Favorite", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    detailMovieViewModel.changeFavorite(true)
                                } else {
                                    lifecycleScope.launch(Dispatchers.IO){
                                        detailMovieViewModel.removeFromFavorite(isFavorite)
                                        runBlocking(Dispatchers.Main){
                                            Toast.makeText(context, it.data?.title + "Remove From Favorite", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    detailMovieViewModel.changeFavorite(false)
                                }
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
        detailMovieViewModel.getDetailMovie(movieId)

        detailMovieViewModel.isFavoriteExist.observe(viewLifecycleOwner){
            if (it){
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_2)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
        lifecycleScope.launch(Dispatchers.IO){
            val fav = detailMovieViewModel.getFavoriteById(movieId)
            if (fav==null){
                detailMovieViewModel.changeFavorite(false)
            }else{
                detailMovieViewModel.changeFavorite(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}