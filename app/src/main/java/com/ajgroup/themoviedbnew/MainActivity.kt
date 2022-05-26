package com.ajgroup.themoviedbnew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ajgroup.themoviedbnew.R
import org.koin.android.BuildConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}