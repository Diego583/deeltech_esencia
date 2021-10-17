package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.GridView
import android.widget.Toast
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_agentes_inmobiliarios.*
import kotlinx.android.synthetic.main.activity_agentes_inmobiliarios.filtroEdo
import kotlinx.android.synthetic.main.activity_proveedores.*
import kotlinx.android.synthetic.main.activity_restauradores.*

class AgentesInmobiliarios : AppCompatActivity() {

    val listItems = arrayOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentes_inmobiliarios)

        var intent: Intent? = null

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("roles", "Agente inmobiliario")
        query.findInBackground { user, e ->
            if (e == null) {
                displayAgentesInmobiliarios(user)
            } else {
                val text = "Error cargando Agentes inmobiliarios"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }

        initializeBackAgent()
        filtrar()
    }

    fun filtrar(){
        filterAgentesInmobiliarios.setOnClickListener{
            var selectedState = ""
            val mBuilder = AlertDialog.Builder(this@AgentesInmobiliarios)
            mBuilder.setTitle("Seleccione los estados deseados")
                .setSingleChoiceItems(listItems, -1) {dialogInterface, i ->
                    selectedState= listItems[i]
                }
                .setPositiveButton("Aceptar",
                    DialogInterface.OnClickListener { dialog, _ ->
                        if (selectedState == "") {
                            filtroEdo.text = ""
                            dialog.dismiss()
                        } else {
                            filtroEdo.text = "Filtrar por estado de $selectedState"
                            dialog.dismiss()
                        }
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            val mDialog = mBuilder.create()
            mDialog.show()
        }
    }

    private fun displayAgentesInmobiliarios(user: MutableList<ParseUser>) {
        val grid: GridView = findViewById(R.id.grid_ai)
        val adapter = ProfileAdapter(this, user)
        grid.setAdapter(adapter)
    }

    private fun initializeBackAgent() {
        backAgentesInmobiliarios.setOnClickListener{
            finish()
        }
    }
}