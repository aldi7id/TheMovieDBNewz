package com.ajgroup.themoviedbnew.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val dataStore: DataStore<Preferences>) {


//    val getNama: Flow<String> = dataStore.data
//        .map { preferences ->
//            preferences[namaKey] ?: ""
//        }
    val getEmail: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[emailKey] ?: ""
        }


    suspend fun setNama(nama: String) {
        dataStore.edit {
            it[namaKey] = nama
        }
    }

//    suspend fun setEmail(email: String) {
//        dataStore.edit {
//            it[emailKey] = email
//        }
//    }

    suspend fun deletePref() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val EMAIL_KEY = "emailkey"
        private const val NAMA_KEY = "jumlahkey"
        val namaKey = stringPreferencesKey(NAMA_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
    }
}