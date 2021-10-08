package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import android.widget.Button
import android.widget.Toast
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onBackPressed() {
        val  builder = AlertDialog.Builder(this)
        builder.setTitle("Atención")
        builder.setMessage("¿Deseas salir de la aplicación?")
        builder.setPositiveButton("Si",{ dialogInterface: DialogInterface, i: Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //prefsUser.clearAllData()

        var intent: Intent? = null

        initializeCategories()
        initializeNavbarHome()
        logOut()
    }

    private fun initializeCategories() {
        cardRestauradores.setOnClickListener {
            intent = Intent(this,Restauradores::class.java)
            startActivity(intent)
        }

        cardAgentesInmobiliarios.setOnClickListener {
            intent = Intent(this,AgentesInmobiliarios::class.java)
            startActivity(intent)
        }

        cardProveedores.setOnClickListener {
            intent = Intent(this,Proveedores::class.java)
            startActivity(intent)
        }
    }

    private fun initializeNavbarHome() {
        bottomNavigationViewHome.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favoritosItem -> {
                    intent = Intent(this,favoritos::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.perfilItem -> {
                    println("Entre a perfiles")
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
                        prefsUser.clearAllData()
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