package com.example.pawmart

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    //setting the time of the splash screen
    private val SPLASH_TIME_OUT: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //variables of all the images
        val logo1 = findViewById<View>(R.id.logo1)
        val logo2 = findViewById<View>(R.id.logo2)
        val appName = findViewById<View>(R.id.appName)
        val rootLayout = findViewById<View>(R.id.rootLayout)

        //animating the images
        val logo1Animator = ObjectAnimator.ofFloat(logo1, "translationX", -1000f, 0f)
        val logo2Animator = ObjectAnimator.ofFloat(logo2, "translationX", 1000f, 0f)
        val appNameAnimator = ObjectAnimator.ofFloat(appName, "translationY", 1000f, 0f)

        //duration of the animation of the images
        logo1Animator.duration = 1000
        logo2Animator.duration = 1000
        appNameAnimator.duration = 1000

        //setting the animation sequence
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(logo1Animator, logo2Animator, appNameAnimator)
        animatorSet.start()

        //to delay the animation after ending
        Handler(Looper.getMainLooper()).postDelayed({
            //fade out the splash screen to white
            rootLayout.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    //start the WelcomeActivity after the fade-out animation
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                    //set the activity transition animation
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
        }, SPLASH_TIME_OUT + 1500) // 1500ms extra for the animation sequence
    }
}
