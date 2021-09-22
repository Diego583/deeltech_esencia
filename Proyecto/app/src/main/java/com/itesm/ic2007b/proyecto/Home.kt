package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cardRestauradores.setOnClickListener {
            var intent: Intent = Intent(this,Restauradores::class.java)
            startActivity(intent)
        }

        cardAgentesInmobiliarios.setOnClickListener {
            var intent: Intent = Intent(this,AgentesInmobiliarios::class.java)
            startActivity(intent)
        }

        cardProveedores.setOnClickListener {
            var intent: Intent = Intent(this,Proveedores::class.java)
            startActivity(intent)
        }

        bottomNavigationViewHome.selectedItemId = R.id.inicioItem

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { menuItem ->
            var intent: Intent? = null
            when (menuItem.itemId) {
                R.id.favoritosItem -> {
                    intent = Intent(this,favoritos::class.java)
                    startActivity(intent)
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

}