package com.ajgroup.themoviedbnew.ui.verif

import android.os.Bundle
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
import androidx.compose.runtime.Composable
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
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ajgroup.themoviedbnew.R
import org.koin.androidx.compose.getViewModel

class LoginFragment : Fragment() {
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
   // private val verifViewModel: VerifViewModel by viewModel()
    //private val composeView = ComposeView(context= applicationCo)


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
                        .background(Color(0xFF032541))){
                    HeaderLogin()
                    InputForm {
                        //LoginForm()
                        Toast.makeText(requireContext(), "Selamat Datang", Toast.LENGTH_SHORT)
                            .show()
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                    ActionItem {
                        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }

//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root

    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.SignUpButton.setOnClickListener {
//            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
//            it.findNavController().navigate(action)
//        }
//        binding.btnLogin.setOnClickListener {
//            if (binding.etEmail.toString().isNotEmpty() && binding.etPassword.toString()
//                    .isNotEmpty()
//            ) {
//                val email = binding.etEmail.text.toString()
//                val pass = binding.etPassword.text.toString()
//                lifecycleScope.launch(Dispatchers.IO) {
//                    val isLogin = verifViewModel.login(email, pass)
//
//                    activity?.runOnUiThread {
//                        if (isLogin == null) {
//                            Toast.makeText(
//                                context,
//                                getString(R.string.email_pass_wrong),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            lifecycleScope.launch(Dispatchers.IO) {
//                                verifViewModel.setEmailPreference(email)
//                                verifViewModel.setNamaPreference(isLogin.name)
//                                runBlocking(Dispatchers.Main) {
//                                    val action = LoginFragmentDirections
//                                        .actionLoginFragmentToHomeFragment()
//                                    it.findNavController().navigate(action)
//                                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
//                                        .show()
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}

@Composable
fun HeaderLogin() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Login", fontSize = 36.sp, style = MaterialTheme.typography.h1, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Image App",
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Crop)
    }
}

@Composable
fun InputForm(OnClickLogin: () -> Unit) {
    val context = LocalContext.current.applicationContext
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
        Button(onClick = {
            val isLogin = vm.login(email.value, password.value)
            if(email.value.isNotEmpty() && password.value.isNotEmpty()){
                if (isLogin == null ){
                    Toast.makeText(context, "UserName / Password Salah", Toast.LENGTH_SHORT).show()
                } else {
                    OnClickLogin.invoke()
                }
            } else {
                Toast.makeText(context, "Username / Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }
            
            }
            , modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Green)) {
            Text(text = "Login")
        }
    }
}
@Composable
fun ActionItem(OnClickRegister: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Belum Punya Akun?", modifier = Modifier.clickable(onClick = OnClickRegister), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Blue))
    }
}

