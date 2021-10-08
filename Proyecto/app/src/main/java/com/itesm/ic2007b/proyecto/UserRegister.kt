package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import com.parse.ParseUser
import java.util.regex.Pattern
import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Editable

import android.text.TextWatcher
import android.widget.TextView
import com.itesm.ic2007b.proyecto.App.Companion.prefs


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

        OnClickUp()
        listenerBtn()
    }

    //Funcion que checa las caracteristicas mínimas que debe tener la contraseña
    fun OnClickUp(){

        val ReglaDigit = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +             //Al menos un digito
                    ".{1,}" +
                    "$"
        )
        val ReglaMinus = Pattern.compile(
            "^" +
                    "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                    ".{1,}" +
                    "$"
        )
        val ReglaMayus = Pattern.compile(
            "^" +
                    "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                    ".{1,}" +
                    "$"
        )
        val ReglaSimbolo = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +        //Al menos 1 simbolo especial
                    ".{1,}" +
                    "$"
        )
        val ReglaChar = Pattern.compile(
            "^" +
                    ".{4,}" +                   //al menos 4 caracteres
                    "$"
        )

        txt_input = binding.contra1 as EditText
        lbl_output = binding.ReglaDigito as TextView


        text = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if(ReglaDigit.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaDigito as TextView
                    lbl_output!!.setText("Un digito ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!ReglaDigit.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaDigito as TextView
                    lbl_output!!.setText("Un digito X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(ReglaMayus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMayus as TextView
                    lbl_output!!.setText("Una letras mayuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaMayus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMayus as TextView
                    lbl_output!!.setText("Una letras mayuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(ReglaMinus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMinus as TextView
                    lbl_output!!.setText("Una letras minuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaMinus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMinus as TextView
                    lbl_output!!.setText("Una letras minuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(ReglaSimbolo.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaSimbolo as TextView
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaSimbolo.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaSimbolo as TextView
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(ReglaChar.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaCaracteres as TextView
                    lbl_output!!.setText("Cuatro caracteres ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!ReglaChar.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaCaracteres as TextView
                    lbl_output!!.setText("Cuatro caracteres X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

            }

            override fun afterTextChanged(editable: Editable) {}

        }
        txt_input!!.addTextChangedListener(text)

    }


    //Funcion que regresa lo que pasaría al momento de darle click al boton de registrar
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
        //Variables
        val UsuarioValue = binding.usuario.text.toString()
        val CorreoValue = binding.email.text.toString()
        val Contra1Value = binding.contra1.text.toString()
        val numeroValue = binding.numero.text.toString()

        /**
         *Aquí se guardan las variables con PREFS
         **/
        prefs.saveUserName(UsuarioValue)
        prefs.saveEmail(CorreoValue)
        prefs.saveContra(Contra1Value)
        prefs.saveNumero(numeroValue)

        val user = ParseUser()
        user.username = UsuarioValue //Usuario
        user.setPassword(Contra1Value) //contraseña
        user.email = CorreoValue //Correo
        user.put("phone", numeroValue)//numero, se crea la columna numero y se guarada ahí

        // other fields can be set just like with ParseObject
        //user.put("phone", "650-253-0000")

        user.signUpInBackground { e ->
            if (e == null) {
                var intent: Intent = Intent(this,Login::class.java)
                //startActivity(intent)
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

