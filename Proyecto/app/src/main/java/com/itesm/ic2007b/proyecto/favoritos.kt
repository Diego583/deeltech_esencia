package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import kotlinx.android.synthetic.main.activity_restauradores.*

/**
 * Activity para mostrar los Favoritos del usuario
 * **/
class favoritos : AppCompatActivity() {

    val favList: ArrayList<String> = App.prefsUser.getFavoritos()
    var userList: ArrayList<ParseUser> = ArrayList()

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

    /**
     * Función que inicializa el boton de regresar del dispositivo
     * para que redirija a Home
     * **/
    override fun onBackPressed() {
        prefsUser.saliFavoritos()
        intent = Intent(this,Home::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        prefsUser.entreFavoritos()

        var intent: Intent? = null

        // Verifica si existen favoritos del usuario
        if (favList.size == 0) {
            val text: String = "No se encontraron favoritos"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        } else {

            for (item in favList) {
                val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
                query.whereEqualTo("username", item)
                query.orderByDescending("username");
                userList.add(query.find()[0])
            }
            displayFavoritos(userList)
        }

        initializeNavbarFav()
        filtrar()
    }

    /**
     * Función donde se inicializan los listeners y métodos para desplegar la lista de estados,
     * realizar la query, y mostrar los favoritos filtrados
     * **/
    fun filtrar(){
        filterFavoritos.setOnClickListener{
            var selectedState = ""
            val mBuilder = AlertDialog.Builder(this@favoritos)
            mBuilder.setTitle("Seleccione los estados deseados")
                .setSingleChoiceItems(listItems, -1) {dialogInterface, i ->
                    selectedState= listItems[i]
                }
                .setPositiveButton("Aceptar",
                    DialogInterface.OnClickListener { dialog, _ ->
                        if (selectedState == "") {
                            filtroEdoFav.text = ""
                            dialog.dismiss()
                        } else {

                            quitarFiltroBtnF.setOnClickListener {
                                quitarFiltroBtnF.visibility = View.GONE
                                filtroEdoFav.text = ""
                                displayFavoritos(userList)
                            }

                            // Se buscan los favoritos por estado
                            var userFiltered : ArrayList<ParseUser> = ArrayList()
                            for (item in userList) {
                                if (item.ubicacion == "$selectedState") {
                                    userFiltered.add(item)
                                }
                            }

                            // Se verifica que existan favoritos en el estado
                            if (userFiltered.size != 0) {
                                filtroEdoFav.text = "Filtrar por estado de $selectedState"
                                dialog.dismiss()
                                quitarFiltroBtnF.visibility = View.VISIBLE
                                displayFavoritos(userFiltered)
                            } else {
                                quitarFiltroBtnF.visibility = View.GONE
                                filtroEdoFav.text = ""
                                val text = "No se encontraron favoritos en $selectedState"
                                val duration = Toast.LENGTH_LONG
                                val toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
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

    /**
     * Función que despliega cada Restaurador dentro de un cardView
     * en el grid usando el Adapter
     * @param MutableList<ParseUser>
     * **/
    private fun displayFavoritos(user: MutableList<ParseUser>) {
        val grid: GridView = findViewById(R.id.grid_Fav)
        val adapter = ProfileAdapter(this, user)
        grid.setAdapter(adapter)
    }

    /**
     * Función que inicializa el navbar
     * y define su las redirecciones
     * por cada botón
     * **/
    private fun initializeNavbarFav() {
        bottomNavigationViewFav.selectedItemId = R.id.favoritosItem

        bottomNavigationViewFav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    prefsUser.saliFavoritos()
                    intent = Intent(this,Home::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.perfilItem -> {
                    prefsUser.saliFavoritos()
                    intent = Intent(this,MiPerfil::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}