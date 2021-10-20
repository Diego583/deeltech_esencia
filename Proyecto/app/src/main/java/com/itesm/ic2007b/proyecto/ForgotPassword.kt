package com.itesm.ic2007b.proyecto

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.itesm.ic2007b.proyecto.databinding.ActivityForgotPasswordBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_forgot_password.*
import com.parse.ParseObject

import com.parse.FindCallback

/**
 * Acivity para cuando el usuario olvida su contraseña
 * @author DeelTech
 **/
class ForgotPassword : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var binding : ActivityForgotPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Aqui se conecta ek .XML con el .KT
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        backForgot.setOnClickListener{
            finish()
        }


        btnEnviar()

    }

    /**
     * Botón que llama a la verificación
     **/
    fun btnEnviar(){
        enviar.setOnClickListener {
            verificación()
        }
    }

    /**
     * Botón que llama a la base de datos para poder recuperar tu contraseña
     * después la base de datos manda un correo al usuario
     **/
    fun enviar(){
            val user = ParseUser()
            ParseUser.requestPasswordReset(resetContraEmail.text.toString());

            val  builder = AlertDialog.Builder(this)
            builder.setTitle("¡Listo!")
            builder.setMessage("Revisa tu correo para restablecer tu contraseña.")
            builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.show()

    }

    /**
     * Se verifican las restricciones
     * Se hace una querie para verificar si existe el Email
     **/
    fun verificación(){

        val email = binding.resetContraEmail.text.toString()

        if(email.isEmpty()){
            val text = "Falta escribir EMAIL"
            val duration = Toast.LENGTH_SHORT

            // Mensaje de error con toast
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }else{

            val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
            query.whereEqualTo("email", email)
            query.findInBackground { user, e ->
                //userEncontrado = user
                if (e == null) {

                    if(user.size == 0){
                        val text = "Correo no existe"
                        val duration = Toast.LENGTH_SHORT

                        // Mensaje de error con toast
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }else if (user.size != 0){
                        enviar()
                    }

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