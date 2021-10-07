package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_splash)
        id_splash_logo.startAnimation(splashAnimation)
        id_splash_title.startAnimation(splashAnimation)
        id_splash_subtitle.startAnimation(splashAnimation)


        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler().postDelayed(
            {
                startActivity(
                    Intent(this@SplashScreenActivity, SubirArchivos::class.java)
                )
                finish()
            },3000
        )
    }



    //var intent: Intent = Intent(this,MainActivity::class.java)
    //startActivity(intent)
}