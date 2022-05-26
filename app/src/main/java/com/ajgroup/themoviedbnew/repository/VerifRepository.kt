package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.local.UserDao
import com.ajgroup.themoviedbnew.data.local.model.User

class VerifRepository(
    private val userDao: UserDao
    ) {

    //ORM
    fun login(email: String, password: String): User? = userDao.login(email, password)
    fun register(user: User):Long = userDao.insertUser(user)
    fun checkEmail(email: String): User? = userDao.checkEmailExist(email)
    suspend fun getUser(email: String): User? = userDao.getUser(email)
    fun updateUser(user: User):Int = userDao.updatetUser(user)
    suspend fun updateAvatarPath(id:Int,avatarPath:String):Int = userDao.updateAvatarPath(id, avatarPath)


}