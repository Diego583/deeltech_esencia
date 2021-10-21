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
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Acivity para navegar e ninicio de la app
 * @author DeelTech
 **/
class Home : AppCompatActivity() {

    /**
     * Muestra mensaje de alerta si es que el usuario quiere salir de la app
     **/
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
        initializeFavotitos()
    }

    private fun initializeFavotitos() {
        prefsUser.clearFavoritos()
        //Agregamos sus favoritos
        val query = ParseQuery.getQuery<ParseObject>("UsuarioFavoritos")
        query.whereEqualTo("username", prefsUser.getUserName())
        query.orderByAscending("username");
        query.findInBackground { favoriteUser, e ->
            if (e == null) {
                for (temp in favoriteUser){
                    prefsUser.saveUserFavoritos(temp.getString("favorito").toString())
                }

            } else {
                val text = "Error cargando datos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
    }

    /**
     * Reedirecciones de todos los botones de la actividad
     **/
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

    /**
     * Reedirecciones de la navbar
     **/
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