package com.ajgroup.themoviedbnew.ui.verif

import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbnew.R
import com.ajgroup.themoviedbnew.data.local.model.User
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

//    private var _binding: FragmentRegisterBinding? = null
//    private val binding get() = _binding!!
   // private val verifViewModel: VerifViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContent {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFF032541))) {
                    HeaderRegister()
                    InputRegisterForm({
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(action)
                    })
                    ActionItemRegister({
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(action)

                    })
                }
            }
        }
//        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
//        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.btnRegister.setOnClickListener {
//            if ((binding.etPassword.text.toString() == binding.etPassword2.text.toString())) {
//
//                val email = binding.etEmail.text.toString()
//                val nama = binding.etName.text.toString()
//                val password = binding.etPassword.text.toString()
//
//
//                val user = User(null, nama, email, password)
//
//                lifecycleScope.launch(Dispatchers.IO) {
//
//                    val isEmailExist = verifViewModel.checkEmail(email)
//
//                    activity?.runOnUiThread {
//                        if (isEmailExist == null) {
//                            registerUser(user)
//                        } else {
//                            Toast.makeText(
//                                context,
//                                getString(R.string.email_registered),
//                                Toast.LENGTH_SHORT
//                            )
//                                .show()
//                        }
//                    }
//
//                }
//            }
//
//        }
//    }

//    private fun registerUser(user: User) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val userIsRegister = verifViewModel.register(user)
//
//            activity?.runOnUiThread {
//                if (userIsRegister == (0).toLong()) {
//                    Toast.makeText(context, getString(R.string.register_failed), Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(
//                        context,
//                        getString(R.string.register_success),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    findNavController().popBackStack()
//                }
//            }
//        }
//
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}
@Composable
fun HeaderRegister() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Register", fontSize = 36.sp, style = MaterialTheme.typography.h1, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Image App",
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Crop)
    }
}
@Composable
fun InputRegisterForm(OnClickLogin:() -> Unit) {
    val context = LocalContext.current
    val vm = getViewModel<VerifViewModel>()
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(32.dp))
        val email = remember {
            mutableStateOf("")
        }
        TextField(
            value = email.value,
            onValueChange = {email.value = it},
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            label = { Text(text = "Your Email") },
            placeholder = { Text(text = "Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))
        val name = remember {
            mutableStateOf("")
        }
        TextField(
            value = name.value,
            onValueChange = {name.value = it},
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon") },
            label = { Text(text = "Your Full Name") },
            placeholder = { Text(text = "Full Name")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val password = remember {
            mutableStateOf("")
        }
        TextField(
            value = password.value,
            onValueChange = {password.value = it},
            leadingIcon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = "emailIcon") },
            label = { Text(text = "Your Password") },
            placeholder = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val repassword = remember {
            mutableStateOf("")
        }
        TextField(
            value = repassword.value,
            onValueChange = {repassword.value = it},
            leadingIcon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = "emailIcon") },
            label = { Text(text = "Repeat Your Password") },
            placeholder = { Text(text = "Repeat Your Password")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val user = User(
                id = null,
                name = name.value,
                email = email.value,
                password = password.value,
                avatarPath = ""
            )
                    if(password.value == repassword.value && password.value.isNotEmpty() && repassword.value.isNotEmpty()){
                        vm.register(user)
                        Toast.makeText(context, "Register ${name.value} Success", Toast.LENGTH_SHORT).show()
                        OnClickLogin.invoke()
                    } else {
                        Toast.makeText(context, "Password Harus Sama / Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                    }


        }, modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Green)) {
            Text(text = "Register")
        }

    }
}
@Composable
fun ActionItemRegister(OnClickLogin:() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Sudah Punya Akun? Login", modifier = Modifier.clickable(onClick = OnClickLogin), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Blue))
    }
}