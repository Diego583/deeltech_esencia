package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import kotlinx.android.synthetic.main.activity_restauradores.*

class Restauradores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restauradores)

        backRestauradores.setOnClickListener{
            finish()
        }

        bottomNavigationViewResta.selectedItemId = R.id.inicioItem

        bottomNavigationViewResta.setOnNavigationItemSelectedListener { menuItem ->
            var intent: Intent? = null
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    intent = Intent(this,Home::class.java)
                    startActivity(intent)
                    true
                }
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