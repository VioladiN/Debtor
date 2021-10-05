package com.violadin.debtorpit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import com.violadin.debtorpit.R

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }

    override fun onResume() {
        super.onResume()

        Handler(Looper.getMainLooper()).postDelayed({showMainActivity()}, 500)

    }

    private fun showMainActivity() {
        val intent = Intent(this, BottomNavBarActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val activityNavigator = ActivityNavigator(this)
        activityNavigator.navigate(
            activityNavigator.createDestination().setIntent(intent), null, null, null
        )
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
        finish()
    }

}