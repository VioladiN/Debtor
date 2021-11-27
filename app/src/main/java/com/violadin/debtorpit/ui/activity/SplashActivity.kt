package com.violadin.debtorpit.ui.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.ActivityNavigator
import com.violadin.debtorpit.R
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }

    override fun onResume() {
        super.onResume()

        showAnim(logo)
        showAnim(text)

        Handler(Looper.getMainLooper()).postDelayed({showMainActivity()}, 1000)

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

    private fun showAnim(v: View) {
        val fadeAnim = ObjectAnimator.ofFloat(v, "alpha", 0.0f, 1.0f)
        fadeAnim.duration = 1000
        fadeAnim.start()
    }

}