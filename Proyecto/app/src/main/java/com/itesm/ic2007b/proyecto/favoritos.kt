package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_proveedores.*

class favoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        bottomNavigationViewFav.selectedItemId = R.id.favoritosItem

        bottomNavigationViewFav.setOnNavigationItemSelectedListener { menuItem ->
            var intent: Intent? = null
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    intent = Intent(this,Home::class.java)
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