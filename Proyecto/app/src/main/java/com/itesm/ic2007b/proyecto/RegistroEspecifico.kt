package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityRolesBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_registro_especifico.*
import kotlinx.android.synthetic.main.activity_registro_especifico.spinner
import kotlinx.android.synthetic.main.activity_roles.*

class RegistroEspecifico : AppCompatActivity() {

    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityRegistroEspecificoBinding
    private lateinit var btnTerminar: Button

    // list of spinner items
    val list = mutableListOf(
        "Aguascalientes",
        "Baja California",
        "Baja California Sur",
        "Campeche",
        "CDMX",
        "Chiapas",
        "Chihuahua",
        "Coahuila",
        "Colima",
        "Durango",
        "Guanajuato",
        "Guerrero",
        "Hidalgo",
        "Jalisco",
        "Edo. Mex.",
        "Michoacán",
        "Morelos",
        "Nayarit",
        "Nuevo León",
        "Oaxaca",
        "Puebla",
        "Querétaro",
        "Quintana Roo",
        "San Luis Potosí",
        "Sinaloa",
        "Sonora",
        "Tabasco",
        "Tamaulipas",
        "Tlaxcala",
        "Veracruz",
        "Yucatán",
        "Zacatecas"

    )

    var vacio: Boolean = false
    var estado_usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner()
        initializeComponents()
        listenerBtn()
    }

    fun spinner(){
        val context = this

        // add a hint to spinner
        // list first item will show as hint
        list.add(0,"Estado...")

        // initialize an array adapter for spinner
        val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {



                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#f25f8d"))
                }

                // set selected item style
                if (position != spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#FFFFFF"))
                    view.setTextColor(Color.parseColor("#000000"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }

        // finally, data bind spinner with adapter
        spinner.adapter = adapter


        // spinner on item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                // by default spinner initial selected item is first item
                if (position != 0){
                    vacio = true
                    estado_usuario = list[position]
                    Toast.makeText(this@RegistroEspecifico, "Entonces seras un "+list[position], Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
                vacio = false
            }
        }
    }

    fun initializeComponents(){
        btnTerminar = findViewById(R.id.btnTerminar)

    }

    fun listenerBtn(){

        btnTerminar.setOnClickListener{

            val descripcion = binding.descripcion.text.toString()
            var estado = estado_usuario


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
                prefsRegister.saveEstado(estado)
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
                user.put("ubicacion", prefsRegister.getEstado())//rol, se crea la columna estado y se guarda ahí


                Log.d("USERNAME", prefsRegister.getUserName());
                Log.d("ESTADO", prefsRegister.getEstado());
                Log.d("CONTRA", prefsRegister.getContra());
                Log.d("DESC", prefsRegister.getDescricpion());

                user.signUpInBackground { e ->
                    if (e == null) {
                        prefsRegister.clearAllData()

                        val text = "Cuenta creada con exito"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()

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