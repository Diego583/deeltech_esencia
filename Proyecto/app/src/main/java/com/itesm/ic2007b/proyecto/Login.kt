package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.parse.Parse


class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnRegister:Button
    private lateinit var btnForgot:Button
    //private lateinit var btnDonate:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkUserSession()
    }

    fun login(username: String, password: String) {
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, parseException: ParseException? ->
            if (parseUser != null) {
                //Si son correctas los guardamos la sesion del usuario
                prefsUser.saveUserName(username)
                prefsUser.saveContra(password)

                val text = "Bienvenido " + username + "!!";
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

                //Redireccionamos ha home
                var intent: Intent = Intent(this,Home::class.java)
                startActivity(intent)
                finish()

            } else {
                ParseUser.logOut()
                if (parseException != null) {
                    Toast.makeText(this, parseException.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    fun initializeComponents(){
        //btnDonate = findViewById(R.id.donar)
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
                login(UsernameValue, ContraValue)

            }
            else{
                val text = "Te faltan campos por llenar"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }

        btnRegister.setOnClickListener{
            //prefsRegister.clearAllData()
            var intent: Intent = Intent(this,UserRegister::class.java)
            startActivity(intent)
            finish()
        }

        btnForgot.setOnClickListener{
            var intent: Intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

        /*btnDonate.setOnClickListener {
            var intent: Intent = Intent(this,Donativos::class.java)
            startActivity(intent)
        }*/

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