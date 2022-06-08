package com.ajgroup.themoviedbnew.repository

import androidx.lifecycle.asLiveData
import com.ajgroup.themoviedbnew.data.local.UserDao
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.data.local.model.User

class VerifRepository(
    private val userDao: UserDao,
    private val userPref: UserDataStoreManager
) {
    //data store
    suspend fun setEmail(email: String) = userPref.setEmail(email)
    suspend fun setNama(nama: String) = userPref.setNama(nama)
    fun getEmail() = userPref.getEmail.asLiveData()
    suspend fun deletePref() = userPref.deletePref()

    //ORM
    fun login(email: String, password: String): User? = userDao.login(email, password)
    fun register(user: User): Long = userDao.insertUser(user)
    fun checkEmail(email: String): User? = userDao.checkEmailExist(email)
    suspend fun getUser(email: String): User? = userDao.getUser(email)
    fun updateUser(user: User): Int = userDao.updatetUser(user)
    suspend fun updateAvatarPath(id: Int, avatarPath: String): Int =
        userDao.updateAvatarPath(id, avatarPath)


}