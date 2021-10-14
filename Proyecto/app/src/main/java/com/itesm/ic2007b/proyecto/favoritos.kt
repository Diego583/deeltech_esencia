package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import kotlinx.android.synthetic.main.activity_restauradores.*

class favoritos : AppCompatActivity() {

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

    override fun onBackPressed() {
        intent = Intent(this,Home::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        var intent: Intent? = null

        initializeNavbarFav()
        filtrar()
    }

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
                            filtroEdoFav.text = "Filtrar por estado de $selectedState"
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

    private fun initializeNavbarFav() {
        bottomNavigationViewFav.selectedItemId = R.id.favoritosItem

        bottomNavigationViewFav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    intent = Intent(this,Home::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.perfilItem -> {
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