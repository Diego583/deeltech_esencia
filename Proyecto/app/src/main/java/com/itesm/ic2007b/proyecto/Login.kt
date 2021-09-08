package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.navigateUp
//import androidx.navigation.ui.setupActionBarWithNavController
//import com.itesm.ic2007b.proyecto.databinding.ActivityLoginBinding

var mail = arrayOf("e1000lio2000@gmail.com", "yaso@gmail.com", "Leonardo")
var pass = arrayOf("Aa123456789:v", "yas.02011","Leonardo")

class Login : AppCompatActivity() {

    private lateinit var btnLogin:Button
    private lateinit var btnRegistrarse: Button
    private lateinit var btnOlvideContrasena: Button
    private lateinit var correo: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeComponents()
        initialzeListeners()

    }

    fun initializeComponents(){
        btnLogin=findViewById(R.id.buttonLogin)
        btnRegistrarse=findViewById(R.id.buttonRegistrarse)
        btnOlvideContrasena=findViewById(R.id.buttonOlvideContrasena)
    }

    fun initialzeListeners(){
        btnLogin.setOnClickListener{
            correo=findViewById(R.id.editTextEmail)
            password=findViewById(R.id.editTextPassword)
            var cor: String = correo.getText().toString().trim()
            var pas: String = password.getText().toString().trim()
            var logeado : Int = 0
            for(i in mail.indices) {
                if(cor==(mail[i]) && pas==(pass[i])){
                    logeado=1
                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            if(logeado==0){
                Toast.makeText(getApplicationContext(),"Email o contraseña incorrectos",Toast.LENGTH_SHORT).show();
            }
        }
        btnRegistrarse.setOnClickListener{
            var intent:Intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnOlvideContrasena.setOnClickListener{
            var intent:Intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}