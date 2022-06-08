package com.ajgroup.themoviedbnew.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.api.Status
import com.ajgroup.themoviedbnew.data.api.model.DetailResponse
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.databinding.FragmentDetailMovieBinding
import com.ajgroup.themoviedbnew.ui.favorite.FavoriteFragmentDirections
import com.ajgroup.themoviedbnew.ui.home.HomeFragmentDirections
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {
//    private var _binding: FragmentDetailMovieBinding? = null
//    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()
    private var movieDetail: DetailResponse? = null
//    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
//
//
//    private val detailMovieViewModel: DetailMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.secondary)) {
                    Row {
                        HeaderMenu({
                            val action = DetailMovieFragmentDirections.actionDetailMovieFragmentToLoginFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Exit Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {
                            val action =
                                DetailMovieFragmentDirections.actionDetailMovieFragmentToFavoriteFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Favorite Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {
                            val action =
                                DetailMovieFragmentDirections.actionDetailMovieFragmentToProfileFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Profile Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                        ItemDetail()
                    }
                    androidx.compose.material.Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF032541)
                    ) {
                        //ItemDetail()
                    }
                }
            }
        }
    }
    @Composable
    fun HeaderMenu( OnClickExit: () -> Unit, OnClickFavorite:() -> Unit , OnClickProfile: () -> Unit) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            val name = "Aldi Dwi Ferdian"
            Text(text = "Welcome $name" , color = Color.White, fontWeight = FontWeight.Bold , fontSize = 20.sp)
            Image(painter = painterResource(id = R.drawable.ic_exit), contentDescription = "Exit", modifier = Modifier.clickable { OnClickExit.invoke() })
            Image(painter = painterResource(id = R.drawable.ic_favorite), contentDescription = "Favorite", modifier = Modifier.clickable { OnClickFavorite.invoke() })
            Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "Profile", modifier = Modifier.clickable { OnClickProfile.invoke() })
        }
    }
    @Composable
    fun ItemDetail(){
        val movieId = args.movieId
        val vm = getViewModel<DetailMovieViewModel>()
        vm.getDetailMovie(movieId)
        movieDetail?.let {
            CustomDetails(
                title = it.title,
                posterPath = it.posterPath,
                genres = it.genres.map { it.name },
                rating = it.voteAverage.toString(),
                dateLabel = "Releate Date",
                dateData = it.releaseDate,
                tagline = it.tagline,
                overview = it.overview
            )
        }
    }
}
//    ): View {
//        // Inflate the layout for this fragment
//        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.ivProfile.setOnClickListener {
//            val action =
//                DetailMovieFragmentDirections.actionDetailMovieFragmentToProfileFragment()
//            it.findNavController().navigate(action)
//        }
//        binding.ivExit.setOnClickListener {
//            val action =
//                DetailMovieFragmentDirections.actionDetailMovieFragmentToLoginFragment()
//            it.findNavController().navigate(action)
//        }
//        binding.ivFavorite2.setOnClickListener {
//            val action =
//                DetailMovieFragmentDirections.actionDetailMovieFragmentToFavoriteFragment()
//            it.findNavController().navigate(action)
//        }
//
//        val movieId = args.movieId
//        detailMovieViewModel.detailMovie.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.LOADING -> {
//                    binding.apply {
//                        ivHeader.visibility = View.INVISIBLE
//                        ivFavorite.visibility = View.INVISIBLE
//                        tvDesc.visibility = View.INVISIBLE
//                        tvGenre.visibility = View.INVISIBLE
//                        tvJudul.visibility = View.INVISIBLE
//                        tvRelease.visibility = View.INVISIBLE
//                        tvStatus.visibility = View.INVISIBLE
//                        tvTagLine.visibility = View.INVISIBLE
//                    }
//                    Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
//                }
//                Status.SUCCESS -> {
//                    binding.apply {
//                        ivHeader.visibility = View.VISIBLE
//                        ivFavorite.visibility = View.VISIBLE
//                        tvDesc.visibility = View.VISIBLE
//                        tvGenre.visibility = View.VISIBLE
//                        tvJudul.visibility = View.VISIBLE
//                        tvRelease.visibility = View.VISIBLE
//                        tvStatus.visibility = View.VISIBLE
//                        tvTagLine.visibility = View.VISIBLE
//                    }
//                    Toast.makeText(context, getString(R.string.loaded), Toast.LENGTH_SHORT).show()
//                    binding.apply {
//                        tvJudul.text = getString(R.string.tv_tittle).plus(it.data?.title)
//                        tvGenre.text =
//                            getString(R.string.tv_genres).plus(it.data?.genres?.get(0)?.name)
//                                .plus("or").plus(
//                                    it.data?.genres?.get(1)?.name
//                                )
//                        tvRelease.text =
//                            getString(R.string.tv_release_date).plus(it.data?.releaseDate)
//                        tvTagLine.text = getString(R.string.tv_taglines).plus(it.data?.tagline)
//                        tvStatus.text = getString(R.string.tv_status).plus(it.data?.status)
//                        tvDesc.text = getString(R.string.tv_desc).plus(it.data?.overview)
//                        Glide.with(requireContext()).load(IMAGE_BASE + it.data?.backdropPath)
//                            .into(ivHeader)
//                    }
//                    binding.ivFavorite.setOnClickListener { _ ->
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            val isFavorite = detailMovieViewModel.getFavoriteById(movieId)
//                            activity?.runOnUiThread {
//                                if (isFavorite == null) {
//                                    val newFavorite = Favorite(
//                                        id = it.data?.id,
//                                        releaseDate = it.data?.releaseDate ?: "",
//                                        posterPath = it.data?.posterPath,
//                                        title = it.data?.title ?: "",
//                                        voteAverage = it.data?.voteAverage ?: 0.0
//                                    )
//                                    lifecycleScope.launch(Dispatchers.IO) {
//                                        detailMovieViewModel.addToFavorite(newFavorite)
//                                        runBlocking(Dispatchers.Main) {
//                                            Toast.makeText(
//                                                context,
//                                                it.data?.title + "Success Add To Favorite",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }
//                                    }
//                                    detailMovieViewModel.changeFavorite(true)
//                                } else {
//                                    lifecycleScope.launch(Dispatchers.IO) {
//                                        detailMovieViewModel.removeFromFavorite(isFavorite)
//                                        runBlocking(Dispatchers.Main) {
//                                            Toast.makeText(
//                                                context,
//                                                it.data?.title + "Remove From Favorite",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }
//                                    }
//                                    detailMovieViewModel.changeFavorite(false)
//                                }
//                            }
//                        }
//                    }
//                }
//                Status.ERROR -> {
//                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        detailMovieViewModel.getDetailMovie(movieId)
//
//        detailMovieViewModel.isFavoriteExist.observe(viewLifecycleOwner) {
//            if (it) {
//                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_2)
//            } else {
//                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
//            }
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            val fav = detailMovieViewModel.getFavoriteById(movieId)
//            if (fav == null) {
//                detailMovieViewModel.changeFavorite(false)
//            } else {
//                detailMovieViewModel.changeFavorite(true)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}