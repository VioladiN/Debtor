package com.violadin.debtorpit.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.fragments.FirstFragment


class MainActivity() : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.page1 -> {
                        Toast.makeText(this, "page 1", Toast.LENGTH_SHORT).show()
                        openFragment(FirstFragment.newInstance())
                    }
                    R.id.page2 -> {
                        Toast.makeText(this, "page 2", Toast.LENGTH_SHORT).show()
                    }
                    R.id.page3 -> {
                        Toast.makeText(this, "page 3", Toast.LENGTH_SHORT).show()
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
