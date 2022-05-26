package com.ajgroup.themoviedbnew.ui.verif

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import com.ajgroup.themoviedbnew.data.local.model.User
import com.ajgroup.themoviedbnew.databinding.FragmentProfileBinding
import com.ajgroup.themoviedbnew.repository.VerifRepository
import com.ajgroup.themoviedbnew.utils.PermissionUtils
import com.ajgroup.themoviedbnew.utils.StorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProfileFragment : Fragment() {
    private var imageUri: Uri? = null
    private var imageSource = -1
    var iduser: Int? = -1
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

//    private val verifViewModel by viewModels<VerifViewModel> {
//        VerifViewModelFactory(
//            VerifRepository(
//                UserDatabase.getInstance(requireContext())!!.userDao(),
//                UserDataStoreManager(requireContext())
//            )
//        )
//    }

    private val verifViewModel: VerifViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGallery.setOnClickListener {
            if (PermissionUtils.isPermissionsGranted(requireActivity(), getRequiredPermission()) {
                    activity?.let {
                        requestPermissionLauncher.launch(getRequiredPermission())
                        imageSource = 1
                    }
                }) {
                openGallery()
            }
        }
        binding.btnCamera.setOnClickListener {
            if (PermissionUtils.isPermissionsGranted(requireActivity(), getRequiredPermission()) {
                    activity?.let {
                        requestPermissionLauncher.launch(getRequiredPermission())
                        imageSource = 2
                    }
                }) {
                openCamera()
            }
        }
        var email = ""

        verifViewModel.emailPreference.observe(viewLifecycleOwner){
            email = it
            verifViewModel.getUser(email)
        }


        verifViewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                tvEditProfile.setText(it?.email)
                etName.setText(it?.name)
                etPassword.setText(it?.password)
                if (it?.avatarPath != "") {
                    if (imageUri != null) {
                        ivPhotoProfile.setImageURI(imageUri)
                    } else {
                        ivPhotoProfile.setImageURI(it?.avatarPath?.toUri())
                    }

                }
            }
            iduser = it?.id
        }
        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val avatar = if (imageUri == null) {
                ""
            } else {
                imageUri.toString()
            }
            val user = User(iduser, name, email, password, avatarPath = avatar)

            lifecycleScope.launch(Dispatchers.IO) {
                val updateUser = verifViewModel.updateUser(user)

                activity?.runOnUiThread {
                    if (updateUser != 0) {
                        verifViewModel.getUser(email)
                        Toast.makeText(requireContext(), "update berhasil", Toast.LENGTH_SHORT)
                            .show()
                        verifViewModel.setNamaPreference(name)
                    }
                }
            }
        }

        binding.tvLogout.setOnClickListener {
            verifViewModel.deletePref()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun openImage(imageSource: Int) {
        if (imageSource==1){
            openGallery()
        }else{
            openCamera()
        }
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intentGallery)
    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            openImage(imageSource)
        }
    }
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                val uri = StorageUtils.savePhotoToExternalStorage(
                    context?.contentResolver,
                    UUID.randomUUID().toString(),
                    bitmap
                )
                imageUri = uri
                uri?.let {
                    loadImage(it)
                }
            }
        }

    private fun loadImage(uri: Uri) {
        binding.ivPhotoProfile.setImageURI(uri)
    }
    private fun getRequiredPermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }
    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            imageUri?.let { loadImage(it) }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}