package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_restauradores.*

import android.widget.GridView
import android.widget.SearchView


class Restauradores : AppCompatActivity() {

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

    var estadoActual:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restauradores)

        var intent: Intent? = null

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("roles", "Restaurador")
        query.orderByAscending("username");
        query.findInBackground { user, e ->
            if (e == null) {
                displayRestauradores(user)
            } else {
                val text = "Error cargando Restauradores"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }

        initializeBackResta()
        filtrar()
        busqueda()
    }

    private fun busqueda() {
        val search = findViewById<SearchView>(R.id.searchRestauradores)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchValue: String?): Boolean {
                val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                query.whereStartsWith("username", searchValue)
                query.whereEqualTo("roles", "Restaurador")
                if(estadoActual != ""){
                    query.whereEqualTo("ubicacion", "$estadoActual")
                }
                query.orderByAscending("username");
                query.findInBackground { user, e ->
                    if (e == null) {
                        displayRestauradores(user)
                    } else {
                        val text = "Error"
                        val duration = Toast.LENGTH_SHORT

                        // Mensaje de error con toast
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()

                    }

                }
                return false
            }

            override fun onQueryTextChange(searchValue: String?): Boolean {
                val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                query.whereStartsWith("username", searchValue)
                query.whereEqualTo("roles", "Restaurador")
                if(estadoActual != ""){
                    query.whereEqualTo("ubicacion", "$estadoActual")
                }
                query.orderByAscending("username");
                query.findInBackground { user, e ->
                    if (e == null) {
                        displayRestauradores(user)
                    } else {
                        val text = "Error"
                        val duration = Toast.LENGTH_SHORT

                        // Mensaje de error con toast
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()

                    }

                }
                return false
            }

        })

    }

    fun filtrar(){
        filterRestauradores.setOnClickListener{
            var selectedState = ""
            val mBuilder = AlertDialog.Builder(this@Restauradores)
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

                            quitarFiltroBtnR.setOnClickListener {
                                estadoActual = ""
                                quitarFiltroBtnR.visibility = View.GONE
                                filtroEdo.text = ""
                                val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                                query.whereEqualTo("roles", "Restaurador")
                                query.orderByAscending("username");
                                query.findInBackground { user, e ->
                                    if (e == null) {
                                        displayRestauradores(user)
                                    } else {
                                        val text = "Error cargando Restauradores"
                                        val duration = Toast.LENGTH_LONG
                                        val toast = Toast.makeText(applicationContext, text, duration)
                                        toast.show()
                                    }
                                }
                            }

                            estadoActual = selectedState
                            val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                            query.whereEqualTo("roles", "Restaurador")
                            query.whereEqualTo("ubicacion", "$selectedState")
                            query.orderByAscending("username");
                            query.findInBackground { userEstado, e ->
                                if (e == null && userEstado.size != 0) {
                                    filtroEdo.text = "Filtrar por estado de $selectedState"
                                    dialog.dismiss()
                                    quitarFiltroBtnR.visibility = View.VISIBLE
                                    displayRestauradores(userEstado)
                                } else {
                                    quitarFiltroBtnR.visibility = View.GONE
                                    filtroEdo.text = ""
                                    val text = "No se encontraron restauradores en $selectedState"
                                    val duration = Toast.LENGTH_LONG
                                    val toast = Toast.makeText(applicationContext, text, duration)
                                    toast.show()
                                }
                            }


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


    private fun displayRestauradores(user: MutableList<ParseUser>) {
        val grid:GridView = findViewById(R.id.grid_res)
        val adapter = ProfileAdapter(this, user)
        grid.setAdapter(adapter)
    }

    private fun initializeBackResta() {
        backRestauradores.setOnClickListener{
            finish()
        }
    }
}