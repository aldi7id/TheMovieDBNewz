package com.ajgroup.themoviedbnew.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.api.Resource
import com.ajgroup.themoviedbnew.data.api.Status
import com.ajgroup.themoviedbnew.data.api.model.MoviesResponse
import org.koin.androidx.compose.getViewModel

class HomeFragment : Fragment() {
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//
//    private val homeViewModel: HomeViewModel by viewModel()
//    private val verifViewModel: VerifViewModel by viewModel()

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
                            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Exit Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Favorite Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                            findNavController().navigate(action)
                            Toast.makeText(
                                requireContext(),
                                "Profile Button Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        })

                    }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF032541)
                    ) {
                        MovieScreen {
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
        // Inflate the layout for this fragment
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
    }
    @Composable
    fun MovieScreen(navigateToDetail: (Int) -> Unit){
        val vm = getViewModel<HomeViewModel>()
        val resource by vm.playingNow.observeAsState()
        vm.getAllPlayingNow()
        MovieList(resource, navigateToDetail = navigateToDetail)
    }
    @Composable
    fun MovieList(
        resource: Resource<MoviesResponse>?,
        navigateToDetail: (Int) -> Unit
    ) {
        when(resource?.status){
            Status.LOADING -> {
                var showDialog by remember { mutableStateOf(false) }
                if (showDialog){
                    Dialog(
                        onDismissRequest = { showDialog = false},
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            Status.SUCCESS -> {
                val data = resource.data?.results ?: emptyList()
                LazyColumn{
                    items(items = data ){
                        movie ->
                        CustomItem(movie = movie, navigateToDetail = navigateToDetail)
                    }
                }
            }
            Status.ERROR -> {
                val context = LocalContext.current
                Toast.makeText(context, "Load Error Nih", Toast.LENGTH_SHORT).show()
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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.ivFavorite.setOnClickListener {
//            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
//        }
//        binding.ivProfile.setOnClickListener {
//            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
//        }
//        binding.ivExit.setOnClickListener {
//            verifViewModel.deletePref()
//            it.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
//        }
//        homeViewModel.namaPreference.observe(viewLifecycleOwner) {
//            if (it != "") {
//                binding.welcome.text = "Welcome,\n$it!"
//            }
//        }
//
//        homeViewModel.discoveryMovies.observe(viewLifecycleOwner) {
//            showDiscoveryMovies(it.results)
//        }
//    }
//
//    private fun showDiscoveryMovies(results: List<Result>?) {
//
//        val discoveryAdapter = HomeAdapter {
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
//            findNavController().navigate(action)
//        }
//        discoveryAdapter.submitList(results)
//        binding.rvList.adapter = discoveryAdapter
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}