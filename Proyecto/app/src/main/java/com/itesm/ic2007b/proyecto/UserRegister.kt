package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.widget.EditText
import android.widget.Toast
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_user_register.*
import java.util.regex.Pattern
import android.R
import android.view.View


class UserRegister : AppCompatActivity(){
    // #00ff00

    //variable para OnKeyUp
    //var tv_filter: EditText? = findViewById<EditText>(R.id.contra1) as EditText

    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityUserRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Aqui se conecta ek .XML con el .KT
        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //setContentView(R.layout.activity_user_register)
        binding.backRegister.setOnClickListener {
            finish()
        }

        listenerBtn()
    }


    fun listenerBtn(){

        //variables del XML sin binding
        val Contra1Text = findViewById<EditText>(R.id.contra1)
        val Contra1Value = Contra1Text.text

        val Contra2Text = findViewById<EditText>(R.id.contra2)
        val Contra2Value = Contra2Text.text

        //Requisitos para las contraseñas
        val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +             //Al menos un digito
                    "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                    "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                    "(?=.*[@#$%^&+=])" +        //Al menos 1 simbolo especial
                    "(?=\\S+$)" +               //no espacios
                    ".{4,}" +                   //al menos 4 caracteres
                    "$"

        )

        val ReglaDigit = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +             //Al menos un digito
                    "$"
        )
        val ReglaMinus = Pattern.compile(
            "^" +
                    "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                    "$"
        )
        val ReglaMayus = Pattern.compile(
            "^" +
                    "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                    "$"
        )
        val ReglaSimbolo = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +        //Al menos 1 simbolo especial
                    "$"
        )
        val ReglaChar = Pattern.compile(
            "^" +
                    ".{4,}" +                   //al menos 4 caracteres
                    "$"
        )


        binding.buttonRegistrarse.setOnClickListener{

            val pass = binding.contra1.text.toString()
            val email = binding.email.text.toString()
            val pass2 = binding.contra2.text.toString()
            val user = binding.usuario.text.toString()

            if(pass == "" || email == "" || pass2 == "" || user == ""){

                val text = "Faltan espacios por llenar"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            }
            else{

                if(!passwordRegex.matcher(pass).matches()){
                    binding.contra1.error = "Contraseña muy débil"
                }
                else if (Contra1Value.toString() != Contra2Value.toString()){
                    val text = "Las contraseñas no coinciden"
                    val duration = Toast.LENGTH_SHORT

                    // Mensaje de error con binding
                    binding.contra2.error = "Las contraseñas no coinciden"

                    // Mensaje de error con toast
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
                else{
                    RegistrarUsuario()
                }

            }

        }
    }


    //Se agrega el usuario nuevo a la base de datos
    fun RegistrarUsuario(){

        val UsuarioText = findViewById<EditText>(R.id.usuario)
        val UsuarioValue = UsuarioText.text

        val CorreoText = findViewById<EditText>(R.id.email)
        val CorreoValue = CorreoText.text

        val Contra1Text = findViewById<EditText>(R.id.contra1)
        val Contra1Value = Contra1Text.text

        val user = ParseUser()
        user.username = UsuarioValue.toString() //Usuario
        user.setPassword(Contra1Value.toString()) //contraseña
        user.email = CorreoValue.toString() //Correo

        // other fields can be set just like with ParseObject
        //user.put("phone", "650-253-0000")

        user.signUpInBackground { e ->
            if (e == null) {
                var intent: Intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            } else {
                val text = "Intentalo más tarde"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
    }

}

