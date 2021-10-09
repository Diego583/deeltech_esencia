package com.itesm.ic2007b.proyecto

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.net.NetworkInfo
import android.net.ConnectivityManager
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /**
         *Metodos para verificar la conexióna internet
         **/
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        //Si hay conexión
        if (networkInfo != null && networkInfo.isConnected) {
            prefsRegister.clearAllData()
            successInternet()
        }
        //Si no hay conexión
        else {
            noSuccessInternet()
        }

    }


    /**
     *Funcion de actividades normales cuando hay conexión
     *Se dirije a la pantalla de login
     **/
    fun successInternet(){
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
                    Intent(this@SplashScreenActivity, Login::class.java)
                )
                finish()
            },1500
        )
    }

    /**
     *Funcion de actividades normales cuando no hay conexión
     *Se dirije a la pantalla de NoHayRed
     **/
    fun noSuccessInternet(){
        startActivity(
            Intent(this@SplashScreenActivity, NoHayRed::class.java)
        )
        finish()
    }

    //var intent: Intent = Intent(this,MainActivity::class.java)
    //startActivity(intent)
}