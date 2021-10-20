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
import android.content.DialogInterface
import android.graphics.Color
import android.text.Editable

import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.UserRegisterCheck.checkDigit
import com.itesm.ic2007b.proyecto.UserRegisterCheck.checkMayus
import com.itesm.ic2007b.proyecto.UserRegisterCheck.checkMinus
import com.itesm.ic2007b.proyecto.UserRegisterCheck.checkSymbol
import com.itesm.ic2007b.proyecto.UserRegisterCheck.checkChar
import com.itesm.ic2007b.proyecto.UserRegisterCheck.emptyRegister
import com.itesm.ic2007b.proyecto.UserRegisterCheck.strongPass
import com.itesm.ic2007b.proyecto.UserRegisterCheck.eqPass
import com.parse.ParseQuery

/**
 * Acivity para poder recivir el usuario y la contraseña para registrarse
 * @author DeelTech
 **/
class UserRegister : AppCompatActivity(){
    // #00ff00

    //variable para OnKeyUp
    private var txt_input: EditText? = null
    private var lbl_output: TextView? = null

    private var text: TextWatcher? = null

    lateinit var dataUser:ArrayList<ParseUser>
    var vacio: Boolean = false


    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityUserRegisterBinding

    override fun onBackPressed() {
        intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Aqui se conecta ek .XML con el .KT
        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefsRegister.clearAllData()

        //setContentView(R.layout.activity_user_register)
        binding.backRegister.setOnClickListener {
            intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        OnClickUp()
        listenerBtn()
    }

    //Funcion que checa las caracteristicas mínimas que debe tener la contraseña
    fun OnClickUp(){

        txt_input = binding.contra1 as EditText
        lbl_output = binding.ReglaDigito as TextView


        text = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if(checkDigit(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaDigito as TextView
                    lbl_output!!.setText("Un digito ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!checkDigit(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaDigito as TextView
                    lbl_output!!.setText("Un digito X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(checkMayus(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaLetrasMayus as TextView
                    lbl_output!!.setText("Una letras mayuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!checkMayus(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaLetrasMayus as TextView
                    lbl_output!!.setText("Una letras mayuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(checkMinus(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaLetrasMinus as TextView
                    lbl_output!!.setText("Una letras minuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!checkMinus(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaLetrasMinus as TextView
                    lbl_output!!.setText("Una letras minuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(checkSymbol(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaSimbolo as TextView
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!checkSymbol(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaSimbolo as TextView
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }

                if(checkChar(binding.contra1.text.toString())){
                    lbl_output = binding.ReglaCaracteres as TextView
                    lbl_output!!.setText("Cuatro caracteres ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!checkChar(binding.contra1.text.toString())){
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

        binding.buttonRegistrarse.setOnClickListener{
            val pass = binding.contra1.text.toString()
            val email = binding.email.text.toString()
            val pass2 = binding.contra2.text.toString()
            val user = binding.usuario.text.toString()
            val userrr = binding.usuario.text.toString()
            dataUser = ArrayList()

            val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
            query.whereEqualTo("username", userrr)
            query.findInBackground { user, e ->
                //userEncontrado = user
                if (e == null) {
                    for (temp in user){
                        dataUser.add(temp)
                    }

                    verficarDatos(pass, email, pass2,  userrr, dataUser)


                } else {
                    val text = "Error"
                    val duration = Toast.LENGTH_SHORT

                    // Mensaje de error con toast
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                }

            }



        }
    }

    /**
     * Recibe los datos y los verifica
     * @param pass, email, pass2, user, dataUser
     */
    fun verficarDatos(pass:String, email:String, pass2:String, user:String, dataUser:ArrayList<ParseUser>){

        if(emptyRegister(pass, email, pass2, user)){

            val text = "Faltan espacios por llenar"
            val duration = Toast.LENGTH_SHORT

            // Mensaje de error con toast
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

        }
        else{
            if(!strongPass(pass)){
                val text = "Contraseña muy débil"
                val duration = Toast.LENGTH_SHORT

                binding.contra1.error = "Contraseña muy débil"
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else if (eqPass(pass, pass2)){
                val text = "Las contraseñas no coinciden"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con binding
                binding.contra2.error = "Las contraseñas no coinciden"

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else if(dataUser.size > 0){
                val text = "Usuario no disponible, intenta con otro"
                val duration = Toast.LENGTH_SHORT

                binding.usuario.error = "Usuario no disponible, intenta con otro"
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else{
                RegistrarUsuario()
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
         *Aqui se guardan las variables con PREFS
         **/
        prefsRegister.saveUserName(UsuarioValue)
        prefsRegister.saveEmail(CorreoValue)
        prefsRegister.saveContra(Contra1Value)
        prefsRegister.saveNumero(numeroValue)


        intent = Intent(this, Roles::class.java)
        startActivity(intent)
        finish()
    }


}

