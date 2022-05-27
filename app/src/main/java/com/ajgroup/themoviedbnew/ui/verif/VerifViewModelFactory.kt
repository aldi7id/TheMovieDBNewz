package com.ajgroup.themoviedbnew.ui.verif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajgroup.themoviedbnew.repository.VerifRepository
import java.lang.IllegalArgumentException

class VerifViewModelFactory(private val repository: VerifRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerifViewModel::class.java)) {
            return VerifViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}