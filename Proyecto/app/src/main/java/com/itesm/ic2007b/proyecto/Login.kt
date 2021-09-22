package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.navigateUp
//import androidx.navigation.ui.setupActionBarWithNavController
//import com.itesm.ic2007b.proyecto.databinding.ActivityLoginBinding

var mail = arrayOf("e1000lio2000@gmail.com", "yaso@gmail.com")
var pass = arrayOf("Aa123456789:v", "yas.02011")

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnRegister:Button
    private lateinit var btnForgot:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeComponents()
        initializeListeners()
    }

    fun initializeComponents(){
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.buttonRegistrarse)
        btnForgot = findViewById(R.id.buttonOlvideContrasena)

    }

    fun initializeListeners(){
        btnLogin.setOnClickListener{
            var intent: Intent = Intent(this,Home::class.java)
            startActivity(intent)
            finish()
        }

        btnRegister.setOnClickListener{
            var intent: Intent = Intent(this,UserRegister::class.java)
            startActivity(intent)
        }

        btnForgot.setOnClickListener{
            var intent: Intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

    }

}