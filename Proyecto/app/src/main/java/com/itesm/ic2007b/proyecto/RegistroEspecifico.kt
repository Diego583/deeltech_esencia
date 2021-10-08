package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityRolesBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import kotlinx.android.synthetic.main.activity_registro_especifico.*

class RegistroEspecifico : AppCompatActivity() {

    //Variable para poder conectar .XML a .KT
    //private lateinit var binding : ActivityRegistroEspecificoBinding
    private lateinit var btnTerminar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityRegistroEspecificoBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_registro_especifico)

        initializeComponents()
        listenerBtn()
    }

    fun initializeComponents(){
        btnTerminar = findViewById(R.id.btnTerminar)

    }

    fun listenerBtn(){

        btnTerminar.setOnClickListener{

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            //finish()


        }


    }
}