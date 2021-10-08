package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itesm.ic2007b.proyecto.databinding.ActivityRolesBinding
import kotlinx.android.synthetic.main.activity_roles.*

import android.view.ViewGroup
import android.widget.*
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import kotlinx.android.synthetic.main.activity_user_register.*


class Roles : AppCompatActivity(){

    private lateinit var binding: ActivityRolesBinding
    //val roles: Array<String> = arrayOf("Restaurador", "Agente inmobiliario", "Proveedor", "Usuario normal")

    // list of spinner items
    val list = mutableListOf("Restaurador", "Agente inmobiliario", "Proveedor", "Usuario normal")

    private lateinit var btnRoles: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRolesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent: Intent? = null

        spinner()
        init()
        listenerBtn()

    }


    fun spinner(){
        val context = this

        // add a hint to spinner
        // list first item will show as hint
        list.add(0,"Seleccione lo que quiera ser")

        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

                val view:TextView = super.getDropDownView(position, convertView, parent) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#f25f8d"))
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
                    Toast.makeText(this@Roles, "Entonces seras un "+list[position], Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
    }

    fun init(){
        btnRoles = binding.btnRoles
    }


    fun listenerBtn(){
        val rol = binding.spinner.toString()

        btnRoles.setOnClickListener{

            /**
             *Aquí se guardan las variables con PREFS
             **/
            prefsRegister.saveRol(rol)

            startActivity(Intent(this,RegistroEspecifico::class.java))


        }

    }

}