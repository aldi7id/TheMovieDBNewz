package com.ajgroup.themoviedbnew.ui.verif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbnew.data.local.model.User
import com.ajgroup.themoviedbnew.repository.VerifRepository
import kotlinx.coroutines.launch

class VerifViewModel(
    private val repository: VerifRepository
) : ViewModel() {
    private val _user: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?> = _user

    fun getUser(email: String) {
        viewModelScope.launch {
            val newUser = repository.getUser(email)
            _user.postValue(newUser)
        }
    }

    fun updateUser(user: User) = repository.updateUser(user)
    suspend fun updateAvatarPath(id: Int, avatarPath: String) =
        repository.updateAvatarPath(id, avatarPath)

    fun login(email: String, password: String) = repository.login(email, password)
    fun register(user: User) = repository.register(user)
    fun checkEmail(email: String) = repository.checkEmail(email)

    //login preference
    fun setEmailPreference(email: String) {
        viewModelScope.launch {
            repository.setEmail(email)
        }
    }

    fun setNamaPreference(nama: String) {
        viewModelScope.launch {
            repository.setNama(nama)
        }
    }

    fun deletePref() = viewModelScope.launch {
        repository.deletePref()
    }

    val emailPreference = repository.getEmail()
}
