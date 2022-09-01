package com.violadin.debtorpit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.violadin.debtorpit.R
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.bottom_nav_menu_activity.*
import kotlinx.android.synthetic.main.fragment_header.*

class BottomNavBarActivity : AppCompatActivity() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_menu_activity)

        viewModel = ViewModelProvider(this)[PersonViewModel::class.java]

        setUpNavigation()
    }

    private fun setUpNavigation() {
        bottom_navigation.setOnNavigationItemReselectedListener { }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottom_navigation, navHostFragment.navController)
    }

    fun changeHeader(string: Int) {
        header_text.setText(string)
    }

    override fun onDestroy() {
        viewModel.closeDb()
        super.onDestroy()
    }

}
