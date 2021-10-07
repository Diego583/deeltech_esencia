package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.itesm.ic2007b.proyecto.databinding.ActivitySubirArchivosBinding
import com.itesm.ic2007b.proyecto.databinding.ActivityUserRegisterBinding
import kotlinx.android.synthetic.main.activity_subir_archivos.*
import android.widget.TextView

import android.view.ViewGroup
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_user_register.*


class SubirArchivos : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivitySubirArchivosBinding
    //val roles: Array<String> = arrayOf("Restaurador", "Agente inmobiliario", "Proveedor", "Usuario normal")

    // list of spinner items
    val list = mutableListOf("Restaurador", "Agente inmobiliario", "Proveedor", "Usuario normal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubirArchivosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner()

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
                    Toast.makeText(this@SubirArchivos, "Entonces seras un "+list[position], Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    fun listenerBtn(){
        val UsuarioValue = binding.spinner.toString()


        val user = ParseUser()
        user.username = UsuarioValue //Usuario
        user.setPassword(Contra1Value) //contraseña
        user.email = CorreoValue //Correo
        user.put("phone", numero)//numero, se crea la columna numero y se guarada ahí


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