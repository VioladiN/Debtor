package com.violadin.debtorpit.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.ActivityMainBinding
import com.violadin.debtorpit.databinding.FragmentHeaderBinding
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navController: NavigationManager
    private val viewModel: MainActivityVM by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController.initActivityController(this)
    }

    fun changeHeader(textId: Int) {
        with(binding) {
            header.headerText.text = getString(textId)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        navController.bottomBarController?.let {
            when (it.currentDestination!!.id) {
                R.id.debt_for_me_fragment, R.id.my_debt_fragment, R.id.multi_debt_fragment -> {
                    finish()
                }
            }
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        viewModel.appDataBase.close()
        super.onDestroy()
    }
}