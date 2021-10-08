package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityRolesBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_registro_especifico.*

class RegistroEspecifico : AppCompatActivity() {

    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityRegistroEspecificoBinding
    private lateinit var btnTerminar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponents()
        listenerBtn()
    }

    fun initializeComponents(){
        btnTerminar = findViewById(R.id.btnTerminar)

    }

    fun listenerBtn(){



        btnTerminar.setOnClickListener{

            val descripcion = binding.descripcion.text.toString()

            if(descripcion.isEmpty()){
                val text = "Te falta escribir una descripcion"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else{
                /**
                 *Aquí se guardan las variables con PREFS
                 **/
                prefsRegister.saveDescricpion(descripcion)




                /**
                 *Aquí giardamos en la base de datos con las variables globales
                 **/
                val user = ParseUser()
                user.username = prefsRegister.getUserName() //Usuario
                user.setPassword(prefsRegister.getContra()) //contraseña
                user.email = prefsRegister.getEmail() //Correo
                user.put("phone", prefsRegister.getNumero())//numero, se crea la columna numero y se guarda ahí
                user.put("roles", prefsRegister.getRol())//rol, se crea la columna roles y se guarda ahí
                user.put("descripcion", prefsRegister.getDescricpion())//rol, se crea la columna roles y se guarda ahí

                user.signUpInBackground { e ->
                    if (e == null) {
                        prefsRegister.clearAllData()
                        intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        prefsRegister.clearAllData()
                        val text = "Intentalo más tarde"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }
                }
            }


        }


    }
}