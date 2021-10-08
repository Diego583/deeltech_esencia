package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser
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

        //val text = prefsRegister.getEmail()
        //val duration = Toast.LENGTH_SHORT

        //val toast = Toast.makeText(applicationContext, text, duration)
        //toast.show()

        checkUserSession()
    }


    fun initializeComponents(){
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.buttonRegistrarse)
        btnForgot = findViewById(R.id.buttonOlvideContrasena)
    }

    fun initializeListeners(){

        btnLogin.setOnClickListener{

            val UsernameText = findViewById<EditText>(R.id.editTextUsername)
            val UsernameValue = UsernameText.text.toString()

            val ContraText = findViewById<EditText>(R.id.editTextPassword)
            val ContraValue = ContraText.text.toString()

            if(ContraValue.isNotEmpty() && UsernameValue.isNotEmpty()){
                //Se validan en la base de datos

                //Si son correctas los guardamos la sesion del usuario
                prefsUser.saveUserName(UsernameValue)
                prefsUser.saveContra(ContraValue)

                //Redireccionamos ha home
                var intent: Intent = Intent(this,Home::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val text = "Te faltan campos por llenar"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

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

    fun checkUserSession(){
        if(prefsUser.getUserName().isNotEmpty()){
            //Redireccionamos ha home
            var intent: Intent = Intent(this,Home::class.java)
            startActivity(intent)
            finish()
        }
        else{
            initializeComponents()
            initializeListeners()
        }
    }

}