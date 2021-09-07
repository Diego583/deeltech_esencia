package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }
    fun initializeListeners(){
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                mTextField.setText("done!")
            }
        }.start()

        var intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}