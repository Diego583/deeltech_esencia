package com.itesm.ic2007b.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.itesm.ic2007b.proyecto.databinding.ActivityEditarRegisterBinding
import com.parse.ParseQuery
import com.parse.ParseUser
import com.squareup.picasso.Picasso
import java.util.regex.Pattern

class EditarRegister : AppCompatActivity() {

    //variable para OnKeyUp
    private var txt_input: EditText? = null
    private var lbl_output: TextView? = null
    private var text: TextWatcher? = null


    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityEditarRegisterBinding

    lateinit var dataUser:ArrayList<ParseUser>
    var vacio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsRegister.clearAllData()

        //Aqui se conecta ek .XML con el .KT
        binding = ActivityEditarRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backRegister.setOnClickListener {
            finish()
        }

        datosRegistrados()
        listenerBtn()
        //OnClickUp()


    }

    //Funcion que checa las caracteristicas mínimas que debe tener la contraseña
    /*
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
        txt_input = binding.contra1
        lbl_output = binding.ReglaDigito
        text = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if(ReglaDigit.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaDigito
                    lbl_output!!.setText("Un digito ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!ReglaDigit.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaDigito
                    lbl_output!!.setText("Un digito X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }
                if(ReglaMayus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMayus
                    lbl_output!!.setText("Una letras mayuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaMayus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMayus
                    lbl_output!!.setText("Una letras mayuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }
                if(ReglaMinus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMinus
                    lbl_output!!.setText("Una letras minuscula ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaMinus.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaLetrasMinus
                    lbl_output!!.setText("Una letras minuscula X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }
                if(ReglaSimbolo.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaSimbolo
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if (!ReglaSimbolo.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaSimbolo
                    lbl_output!!.setText("Un simbolo [@#\$%^&+=] X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }
                if(ReglaChar.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaCaracteres
                    lbl_output!!.setText("Cuatro caracteres ✔")
                    lbl_output!!.setTextColor(Color.parseColor("#008000"))
                }
                if(!ReglaChar.matcher(binding.contra1.text.toString()).matches()){
                    lbl_output = binding.ReglaCaracteres
                    lbl_output!!.setText("Cuatro caracteres X")
                    lbl_output!!.setTextColor(Color.parseColor("#ff0000"))
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        }
        txt_input!!.addTextChangedListener(text)
    }
     */

    //Funcion que regresa lo que pasaría al momento de darle click al boton de registrar
    fun listenerBtn(){

        binding.buttonRegistrarse.setOnClickListener{
            val userrr = binding.usuario.text.toString()
            val numero = binding.numero.text.toString()
            dataUser = ArrayList()

            if(userrr == prefsUser.getUserName()){
                if(numero == "" || userrr == ""){

                    val text = "Faltan espacios por llenar"
                    val duration = Toast.LENGTH_SHORT

                    // Mensaje de error con toast
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                }else{
                    RegistrarUsuario()
                }
            }else{

                val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                query.whereEqualTo("username", userrr)
                query.findInBackground { user, e ->
                    //userEncontrado = user
                    if (e == null) {
                        for (temp in user){
                            dataUser.add(temp)
                        }

                        verficarDatos(numero ,userrr, dataUser)


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
    }

    /**
     * Recive los datos y los verifica
     * @param pass, email, pass2, user, dataUser
     */
    fun verficarDatos(numero:String ,user:String, dataUser:ArrayList<ParseUser>){

        if(numero == "" || user == ""){

            val text = "Faltan espacios por llenar"
            val duration = Toast.LENGTH_SHORT

            // Mensaje de error con toast
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

        }
        else{
            if(dataUser.size > 0 ){
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
        val numeroValue = binding.numero.text.toString()

        /**
         *Aqui se guardan las variables con PREFS
         **/
        prefsRegister.saveUserName(UsuarioValue)
        prefsRegister.saveNumero(numeroValue)

        intent = Intent(this, EditarRegistroEspecifico::class.java)
        startActivity(intent)
        finish()
    }


    //Se muestran los datos que ya había puesto el usuario en su registro
    fun datosRegistrados(){
        val nombreG = prefsUser.getUserName()

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val foto: String? = user[0].getParseFile(LLAVE_FOTOPERFIL)?.url
                var descripcion: String = user[0].descripcion.toString()
                val docPDF : String? = user[0].getParseFile(LLAVE_DOCPDF)?.url
                //val contra: String = user[0].password.toString()
                val numero: String = user[0].phone.toString()

                displayData(foto, nombreG, descripcion, docPDF, numero)
            } else {
                val text = "Error cargando datos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }
    }

    fun displayData(fotoUrl:String?, nombre:String, descripcion:String, docUrl:String?, numero:String) {

        binding.usuario.setText(nombre)
        binding.numero.setText(numero)


    }



}