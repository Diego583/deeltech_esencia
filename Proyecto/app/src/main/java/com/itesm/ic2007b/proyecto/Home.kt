package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        /**
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    infoTextView.setText(R.string.inicio)
                    true
                }
                R.id.favoritosItem -> {
                    infoTextView.setText(R.string.favoritos)
                    true
                }
                R.id.perfilItem -> {
                    infoTextView.setText(R.string.perfil)
                    true
                }
                else -> false
            }
        }**/
    }

}