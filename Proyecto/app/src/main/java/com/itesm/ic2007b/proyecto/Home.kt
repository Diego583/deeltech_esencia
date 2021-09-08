package com.itesm.ic2007b.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        RestauradoresImage.setOnClickListener {
            infoTextView.setText(R.string.restauradores)
        }
        AgentImage.setOnClickListener {
            infoTextView.setText(R.string.agentes)
        }
        ProveedoresImage.setOnClickListener {
            infoTextView.setText(R.string.proveedores)
        }

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
        }
    }
}