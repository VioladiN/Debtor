package com.violadin.debtorpit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.violadin.debtorpit.R
import com.violadin.debtorpit.navigation.NavigationManager

class MainActivity : AppCompatActivity() {

    private val navController = NavigationManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController.initActivityController(this)
    }

}