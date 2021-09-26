package com.violadin.debtorpit.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.violadin.debtorpit.R
import com.violadin.debtorpit.ui.fragment.MyDebtFragment
import com.violadin.debtorpit.ui.fragment.MultiDebtFragment
import com.violadin.debtorpit.ui.fragment.DebtForMeFragment
import kotlinx.android.synthetic.main.bottom_nav_menu_activity.*


class BottomNavBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_menu_activity)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        bottom_navigation.setOnNavigationItemReselectedListener { }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottom_navigation, navHostFragment.navController)
    }
}
