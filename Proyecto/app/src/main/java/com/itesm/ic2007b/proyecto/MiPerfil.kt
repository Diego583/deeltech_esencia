package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.parse.*
import kotlinx.android.synthetic.main.activity_mi_perfil.*
import kotlinx.android.synthetic.main.activity_mi_perfil.LogOut







class MiPerfil : AppCompatActivity() {

    override fun onBackPressed() {
        intent = Intent(this,Home::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil)

        var intent: Intent? = null

        val nombreG = App.prefsUser.getUserName()

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val ubicacion: String = user[0].ubicacion.toString()
                val descripcion: String = user[0].descripcion.toString()

                displayData(nombreG, ubicacion, descripcion)
            } else {
                val error: String = "ERROR"
                displayData(error, error, error)
            }

        }

        initializeNavbarMiPerfil()
        logOut()
    }

    private fun displayData(nombre:String, ubicacion:String,
                            descripcion:String) {
        val fotoPerfilImageView: ImageView = findViewById(R.id.foto_perfil)
        val miNombreTextView: TextView = findViewById(R.id.mi_nombre)
        val ubicacionTextView: TextView = findViewById(R.id.ubicacion)
        val descripcionTextView: TextView = findViewById(R.id.descripcion_perfil)

        miNombreTextView.text = nombre
        ubicacionTextView.text = ubicacion
        descripcionTextView.text = descripcion
    }

    private fun initializeNavbarMiPerfil() {
        bottomNavigationViewMiPerfil.selectedItemId = R.id.perfilItem

        bottomNavigationViewMiPerfil.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    intent = Intent(this,Home::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.favoritosItem -> {
                    intent = Intent(this,favoritos::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    fun logOut(){
        LogOut.setOnClickListener{

            val  builder = AlertDialog.Builder(this)
            builder.setTitle("Atención")
            builder.setMessage("¿Deseas cerrar sesión?")
            builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->

                ParseUser.logOutInBackground { e: ParseException? ->
                    if (e == null)
                        App.prefsUser.clearAllData()
                    val text = "Vuelve pronto y recuerda donar!!!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                    intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }


            }

            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()


        }
    }
}