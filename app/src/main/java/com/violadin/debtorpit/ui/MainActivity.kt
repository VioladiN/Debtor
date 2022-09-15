package com.violadin.debtorpit.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.violadin.debtorpit.R
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navController: NavigationManager
    private val viewModel: MainActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController.initActivityController(this)
    }

    override fun onDestroy() {
        viewModel.appDataBase.close()
        super.onDestroy()
    }
}