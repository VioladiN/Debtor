package com.violadin.debtorpit.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.fragments.AddDebtsFragment
import com.violadin.debtorpit.fragments.AddPersonFragment
import com.violadin.debtorpit.fragments.ListFragment


class MainActivity() : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.page1 -> {
                        openFragment(ListFragment.newInstance())
                    }
                    R.id.page2 -> {
                        openFragment(AddPersonFragment.newInstance())
                    }
                    R.id.page3 -> {
                        openFragment(AddDebtsFragment.newInstance())
                    }
                }
            true
        }
    }

    fun openFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }
}
