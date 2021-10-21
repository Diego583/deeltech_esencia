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
import com.itesm.ic2007b.proyecto.LoginCheck.checkValues
import com.parse.*
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Acivity para que el usuario se pueda logear
 * @author DeelTech
 **/
class Login : AppCompatActivity() {

    /**
     * Variables globales
     **/
    private lateinit var btnLogin: Button
    private lateinit var btnRegister:Button
    private lateinit var btnForgot:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkUserSession()
    }

    /**
     * Se verfica si el username y la contraseña son correctos
     * Se inicializa la parte de favoritos del usuario y se agrega sus favoritos
     * @param username
     * @param password
     **/
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

    /**
     * Se inicializan los componentes
     **/
    fun initializeComponents(){
        //btnDonate = findViewById(R.id.donar)
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.buttonRegistrarse)
        btnForgot = findViewById(R.id.buttonOlvideContrasena)
    }

    /**
     * Se inicializan los botónes
     * Se verifican las restricciones
     **/
    fun initializeListeners(){

        btnLogin.setOnClickListener{

            val UsernameText = findViewById<EditText>(R.id.editTextUsername)
            val UsernameValue = UsernameText.text.toString()

            val ContraText = findViewById<EditText>(R.id.editTextPassword)
            val ContraValue = ContraText.text.toString()


            if(checkValues(ContraValue, UsernameValue)){
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

    }

    /**
     * Se checa si el usuario no está vacío y se inicializan los componentes
     **/
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