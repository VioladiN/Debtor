package com.violadin.debtorpit.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.violadin.debtorpit.R
import com.violadin.debtorpit.ui.fragment.MyDebtFragment
import com.violadin.debtorpit.ui.fragment.MultiDebtFragment
import com.violadin.debtorpit.ui.fragment.DebtForMeFragment
import kotlinx.android.synthetic.main.bottom_nav_menu_activity.*


class BottomNavBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_menu_activity)

        val bottomAppBarBackground = bottom_navigation.background as MaterialShapeDrawable
        bottomAppBarBackground.shapeAppearanceModel =
            bottomAppBarBackground.shapeAppearanceModel
                .toBuilder()
                .setTopRightCorner(
                    CornerFamily.ROUNDED,
                    resources.getDimension(R.dimen.bottom_app_bar_corners))
                .setTopLeftCorner(
                    CornerFamily.ROUNDED,
                    resources.getDimension(R.dimen.bottom_app_bar_corners))
                .build()

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
