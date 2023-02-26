package com.violadin.debtorpit.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.ActivityMainBinding
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager
    private val viewModel: MainActivityVM by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Debtorpit)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationManager.initActivityController(this)
    }

    override fun onResume() {
        super.onResume()

        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.activity_container)
        fragmentContainer?.childFragmentManager?.addOnBackStackChangedListener {
            backButtonVisibility(
                fragmentContainer.childFragmentManager.backStackEntryCount,
                navigationManager.mainActivityController
            )
        }

        with(binding) {
            header.commonButton.setOnClickListener {
                val popup = PopupMenu(this@MainActivity, header.commonButton)
                popup.menuInflater
                    .inflate(R.menu.popup_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.history -> {
                            navigationManager.mainActivityController?.navigate(R.id.open_history_fragment)
                            true
                        }
                        R.id.statistics -> {
                            navigationManager.mainActivityController?.navigate(R.id.open_statistics_fragment)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }

            header.imageBack.setOnClickListener {
                navigationManager.activeMavController?.navigateUp()
            }
        }
    }

    fun backButtonVisibility(backStackCount: Int, activeNavController: NavController?) {
        navigationManager.activeMavController = activeNavController
        if (backStackCount > 0) {
            binding.header.imageBack.visibility = View.VISIBLE
        } else {
            binding.header.imageBack.visibility = View.INVISIBLE
        }
    }

    fun changeHeader(textId: Int) {
        with(binding) {
            header.headerText.text = getString(textId)
        }
    }

    override fun onDestroy() {
        viewModel.appDataBase.close()
        super.onDestroy()
    }
}