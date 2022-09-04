package com.violadin.debtorpit.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController.initActivityController(this)
    }

    override fun onDestroy() {
        // todo close db via appDataBase.close()
        super.onDestroy()
    }
}