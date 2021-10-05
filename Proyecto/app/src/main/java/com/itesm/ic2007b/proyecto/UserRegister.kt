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
import android.graphics.Color
import android.view.View
import android.text.Editable

import android.text.TextWatcher
import android.widget.TextView


class UserRegister : AppCompatActivity(){
    // #00ff00

    //variable para OnKeyUp
    private var txt_input: EditText? = null
    private var lbl_output: TextView? = null

    private var text: TextWatcher? = null


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


    fun OnClickUp(rule: String){

        txt_input = binding.contra1.text as EditText
        lbl_output = binding.ReglaDigito.text as TextView

        text = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                lbl_output!!.setTextColor(Color.GREEN)
            }
            override fun afterTextChanged(editable: Editable) {}
        }
        txt_input!!.addTextChangedListener(text)

    }



    fun listenerBtn(){

        //variables del XML sin binding
        val Contra1Value = binding.contra1.text.toString()

        val Contra2Value = binding.contra2.text.toString()

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
                else if (Contra1Value != Contra2Value){
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

        val UsuarioValue = binding.usuario.text.toString()

        val CorreoValue = binding.email.text.toString()

        val Contra1Value = binding.contra1.text.toString()

        val user = ParseUser()
        user.username = UsuarioValue //Usuario
        user.setPassword(Contra1Value) //contraseña
        user.email = CorreoValue //Correo

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

