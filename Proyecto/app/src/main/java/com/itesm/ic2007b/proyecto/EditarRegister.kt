package com.itesm.ic2007b.proyecto

import android.annotation.SuppressLint
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.itesm.ic2007b.proyecto.databinding.ActivityEditarRegisterBinding
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import com.squareup.picasso.Picasso
import java.util.regex.Pattern

class EditarRegister : AppCompatActivity() {

    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityEditarRegisterBinding

    lateinit var dataUser:ArrayList<ParseUser>
    var vacio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Aqui se conecta ek .XML con el .KT
        binding = ActivityEditarRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefsRegister.clearAllData()
        binding.backRegister.setOnClickListener {
            finish()
        }

        datosRegistrados()
        listenerBtn()

    }


    /**
     *AQUÍ SE AGREGA EL PEDO
     **/
    fun listenerBtn(){

        binding.buttonRegistrarse.setOnClickListener{

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


    /**
     *Se hace una query para revisar cuales fueron
     *los datos que el usuario puso en su registro
     **/
    fun datosRegistrados(){
        val nombreG = prefsUser.getUserName()

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val foto: String? = user[0].getParseFile(LLAVE_FOTOPERFIL)?.url
                var descripcion: String = user[0].descripcion.toString()
                val docPDF : String? = user[0].getParseFile(LLAVE_DOCPDF)?.url
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

    /**
     *Se muestran los datos de "datosRegistrados()"
     **/
    fun displayData(fotoUrl:String?, nombre:String, descripcion:String, docUrl:String?, numero:String) {

        binding.usuario.setText(nombre)
        binding.numero.setText(numero)

    }



}