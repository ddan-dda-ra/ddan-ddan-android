package com.ddanddan.ddanddan.presentation

import android.os.Bundle
import android.util.Log
import com.ddanddan.base.BindingActivity
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}