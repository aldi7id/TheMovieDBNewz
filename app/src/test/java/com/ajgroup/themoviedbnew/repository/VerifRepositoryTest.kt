package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.local.UserDao
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager
import com.ajgroup.themoviedbnew.data.local.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class VerifRepositoryTest {
    private lateinit var userDao: UserDao
    private lateinit var userDataStoreManager: UserDataStoreManager

    private lateinit var verifRepository: VerifRepository

    @Before
    fun setUp() {
        userDao = mockk()
        userDataStoreManager = mockk()
        verifRepository = VerifRepository(userDao, userDataStoreManager)
    }

    @Test
    fun login() {
        val returnLogin = mockk<User>()

        every {
            runBlocking {
                userDao.login("email@gmail.com", "password")
            }
        } returns returnLogin

        verifRepository.login("email@gmail.com", "password")

        verify {
            runBlocking {
                userDao.login("email@gmail.com", "password")
            }
        }
    }
}